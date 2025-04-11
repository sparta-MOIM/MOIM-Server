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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    String role = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    String accessToken = jwtUtil.createAccessToken(username, role, trackingId, now);
    String refreshToken = jwtUtil.createRefreshToken(username, trackingId, now);

    ResponseCookie accessTokenCookie = createAccessTokenCookie(accessToken);
    ResponseCookie refreshTokenCookie = createRefreshTokenCookie(refreshToken);

    response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
  }

  private static ResponseCookie createRefreshTokenCookie(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .path("/api/v1/auth/refresh")
        .httpOnly(true)
        .secure(true)
        .sameSite(SameSite.STRICT.name())
        .maxAge(JwtUtil.REFRESH_TOKEN_EXPIRY_SECOND)
        .build();
  }

  private static ResponseCookie createAccessTokenCookie(String accessToken) {
    return ResponseCookie.from("accessToken", accessToken)
        .path("/")
        .httpOnly(true)
        .secure(true)
        .sameSite(SameSite.LAX.name())
        .maxAge(JwtUtil.ACCESS_TOKEN_EXPIRY_SECOND)
        .build();
  }
}
