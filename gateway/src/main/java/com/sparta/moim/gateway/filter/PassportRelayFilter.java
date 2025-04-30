package com.sparta.moim.gateway.filter;

import static com.sparta.moim.common.passport.enums.UserPassportConstants.X_USER_ID;
import static com.sparta.moim.common.passport.enums.UserPassportConstants.X_USER_NAME;
import static com.sparta.moim.common.passport.enums.UserPassportConstants.X_USER_ROLE;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.ACCESS_TOKEN_COOKIE_IS_EMPTY;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.ACCESS_TOKEN_COOKIE_NOT_FOUND;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.PASSPORT_RETRIEVAL;

import com.sparta.moim.common.passport.Passport;
import com.sparta.moim.gateway.exception.CookieNotFoundException;
import com.sparta.moim.gateway.exception.PassportRetrievalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class PassportRelayFilter extends AbstractGatewayFilterFactory<Object> {

  private final WebClient webClient;
  private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";
  private static final String GET_PASSPORT_URL = "/internal/v1/users/passport";

  public PassportRelayFilter(WebClient.Builder webClientBuilder) {
    super(Object.class);
    this.webClient = webClientBuilder
        .baseUrl("lb://user-service")
        .build();
  }

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      log.info("PassportRelayFilter apply={}", exchange.getRequest().getPath());
      log.info("PassportRelayFilter pre traceparent={}", exchange.getRequest().getHeaders().getFirst("traceparent"));
      String accessToken = extractAccessTokenFromCookie(exchange);
      return webClient.get()
          .uri(GET_PASSPORT_URL)
          .header(ACCESS_TOKEN_COOKIE_NAME, accessToken)
          .retrieve()
          .bodyToMono(Passport.class)
          .onErrorMap(original -> new PassportRetrievalException(PASSPORT_RETRIEVAL))
          .flatMap(passport -> {
            ServerHttpRequest request = exchange.getRequest().mutate()
                .header(X_USER_ID.getValue(), passport.userTrackingId())
                .header(X_USER_NAME.getValue(), passport.username())
                .header(X_USER_ROLE.getValue(), passport.role())
                .build();

            log.info("PassportRelayFilter post traceparent={}", exchange.getResponse().getHeaders().getFirst("traceparent"));
            return chain.filter(exchange.mutate().request(request).build());
          });
    };
  }

  private String extractAccessTokenFromCookie(ServerWebExchange exchange) {
    HttpCookie accessTokenCookie = exchange.getRequest().getCookies().getFirst(ACCESS_TOKEN_COOKIE_NAME);

    if (accessTokenCookie == null) {
      throw new CookieNotFoundException(ACCESS_TOKEN_COOKIE_NOT_FOUND);
    }

    String accessToken = accessTokenCookie.getValue();
    if (!StringUtils.hasText(accessToken)) {
      throw new CookieNotFoundException(ACCESS_TOKEN_COOKIE_IS_EMPTY);
    }
    return accessToken;
  }
}
