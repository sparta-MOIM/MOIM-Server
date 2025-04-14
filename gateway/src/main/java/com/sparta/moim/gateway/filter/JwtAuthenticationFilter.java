package com.sparta.moim.gateway.filter;

import static com.sparta.moim.common.enums.UserPassportConstants.X_USER_ID;
import static com.sparta.moim.common.enums.UserPassportConstants.X_USER_NAME;
import static com.sparta.moim.common.enums.UserPassportConstants.X_USER_ROLE;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.ACCESS_TOKEN_IS_EMPTY;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.ACCESS_TOKEN_NOT_FOUND;

import com.sparta.moim.gateway.JwtUtil;
import com.sparta.moim.gateway.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<Object> {

  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    super(Object.class);
    this.jwtUtil = jwtUtil;
  }

  private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      String accessToken = extractAccessTokenFromCookie(exchange);
      Claims claims = jwtUtil.extractClaims(accessToken);
      ServerWebExchange addedPassportExchange = addPassportToHeader(exchange, claims);
      return chain.filter(addedPassportExchange);
    };
  }

  private ServerWebExchange addPassportToHeader(ServerWebExchange exchange, Claims claims) {
    String trackingId = claims.getSubject();
    String username = claims.get("username", String.class);
    String role = claims.get("role", String.class);

    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
        .header(X_USER_ID.name(), trackingId)
        .header(X_USER_NAME.name(), username)
        .header(X_USER_ROLE.name(), role)
        .build();

    return exchange.mutate().request(mutatedRequest).build();
  }

  private String extractAccessTokenFromCookie(ServerWebExchange exchange) {
    HttpCookie accessTokenCookie = exchange.getRequest().getCookies().getFirst(ACCESS_TOKEN_COOKIE_NAME);

    if (accessTokenCookie == null) {
      throw new JwtAuthenticationException(ACCESS_TOKEN_NOT_FOUND);
    }

    String accessToken = accessTokenCookie.getValue();
    if (!StringUtils.hasText(accessToken)) {
      throw new JwtAuthenticationException(ACCESS_TOKEN_IS_EMPTY);
    }
    return accessToken;
  }
}
