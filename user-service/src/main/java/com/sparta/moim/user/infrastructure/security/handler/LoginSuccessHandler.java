package com.sparta.moim.user.infrastructure.security.handler;

import com.sparta.moim.user.infrastructure.jwt.JwtUtil;
import com.sparta.moim.user.infrastructure.redis.model.RefreshToken;
import com.sparta.moim.user.infrastructure.redis.repository.RefreshTokenRepository;
import com.sparta.moim.user.infrastructure.service.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    log.info("traceparent={}", request.getHeader("traceparent"));
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    UUID trackingId = userDetails.getTrackingId();

    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    UUID accessTokenId = UUID.randomUUID();
    String accessToken = jwtUtil.createAccessToken(username, trackingId, accessTokenId, now);

    UUID refreshTokenId = UUID.randomUUID();
    String refreshToken = jwtUtil.createRefreshToken(username, trackingId, refreshTokenId, now);
    log.info("Successfully create token");

    ResponseCookie accessTokenCookie = jwtUtil.createAccessTokenCookie(accessToken);
    ResponseCookie refreshTokenCookie = jwtUtil.createRefreshTokenCookie(refreshToken);
    log.info("Successfully create token cookie");

    refreshTokenRepository.save(new RefreshToken(refreshTokenId.toString()));
    log.info("Successfully save token storage");

    response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
  }
}
