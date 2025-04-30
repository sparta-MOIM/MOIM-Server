package com.sparta.moim.user.infrastructure.security.handler;

import com.sparta.moim.user.constants.JwtConstants.CookieName;
import com.sparta.moim.user.infrastructure.jwt.JwtUtil;
import com.sparta.moim.user.infrastructure.redis.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Slf4j
@RequiredArgsConstructor
public class TokenClearingLogoutHandler implements LogoutHandler {

  private final JwtUtil jwtUtil;
  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
                     Authentication authentication) {
    log.info("로그아웃 중: 토큰 클리어링 작업 시작");

    String refreshToken = null;
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (CookieName.REFRESH_TOKEN.equals(cookie.getName())) {
          refreshToken = cookie.getValue();
          break;
        }
      }
    }

    if (refreshToken != null) {
      Claims claims = jwtUtil.extractClaims(refreshToken);
      String jti = claims.getId();
      refreshTokenRepository.deleteById(jti);
      log.info("로그아웃 중: 저장된 리프래시 토큰 삭제 완료");
    }
  }
}