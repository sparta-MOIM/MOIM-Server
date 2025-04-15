package com.sparta.moim.user.infrastructure.security.handler;

import com.sparta.moim.user.infrastructure.jwt.JwtUtil;
import com.sparta.moim.user.infrastructure.service.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    UUID trackingId = userDetails.getTrackingId();
    String role = userDetails.getRole();

    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    String accessToken = jwtUtil.createAccessToken(username, role, trackingId, now);
    String refreshToken = jwtUtil.createRefreshToken(username, trackingId, now);

    ResponseCookie accessTokenCookie = jwtUtil.createAccessTokenCookie(accessToken);
    ResponseCookie refreshTokenCookie = jwtUtil.createRefreshTokenCookie(refreshToken);

    response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
  }
}
