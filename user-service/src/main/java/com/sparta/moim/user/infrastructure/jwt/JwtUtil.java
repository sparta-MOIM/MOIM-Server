package com.sparta.moim.user.infrastructure.jwt;

import static com.sparta.moim.user.application.exception.AuthErrorCode.EXPIRED_JWT;
import static com.sparta.moim.user.application.exception.AuthErrorCode.JWT_NOT_VALID;
import static com.sparta.moim.user.application.exception.AuthErrorCode.MALFORMED_JWT;
import static com.sparta.moim.user.application.exception.AuthErrorCode.SIGNATURE_NOT_VALID;
import static com.sparta.moim.user.application.exception.AuthErrorCode.UNSUPPORTED_JWT;

import com.sparta.moim.user.application.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  public static final int ACCESS_TOKEN_EXPIRY_SECOND = 60 * 30;
  public static final int REFRESH_TOKEN_EXPIRY_SECOND = 60 * 60 * 24 * 7;

  private final SecretKey secretKey;
  private final String issuer;

  public JwtUtil(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.issuer}") String issuer
  ) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    this.issuer = issuer;
  }

  public String createAccessToken(String username, String role, UUID trackingId, ZonedDateTime now) {
    Date expiration = Date.from(now.plusSeconds(ACCESS_TOKEN_EXPIRY_SECOND).toInstant());
    return Jwts.builder()
        .setSubject(trackingId.toString())
        .claim("username", username)
        .setIssuer(issuer)
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(expiration)
        .signWith(secretKey)
        .compact();
  }

  public String createRefreshToken(String username, UUID trackingId, ZonedDateTime now) {
    Date expiration = Date.from(now.plusSeconds(REFRESH_TOKEN_EXPIRY_SECOND).toInstant());
    return Jwts.builder()
        .setSubject(trackingId.toString())
        .claim("username", username)
        .setIssuer(issuer)
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(expiration)
        .signWith(secretKey)
        .compact();
  }

  public Claims extractClaims(String token) {
    Claims claims;
    try {
      claims = Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      throw new JwtAuthenticationException(EXPIRED_JWT);
    } catch (UnsupportedJwtException e) {
      throw new JwtAuthenticationException(UNSUPPORTED_JWT);
    } catch (MalformedJwtException e) {
      throw new JwtAuthenticationException(MALFORMED_JWT);
    } catch (SignatureException e) {
      throw new JwtAuthenticationException(SIGNATURE_NOT_VALID);
    } catch (IllegalArgumentException e) {
      throw new JwtAuthenticationException(JWT_NOT_VALID);
    }
    return claims;
  }

  public ResponseCookie createRefreshTokenCookie(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .path("/api/v1/auth/refresh")
        .httpOnly(true)
        .secure(true)
        .sameSite(SameSite.STRICT.name())
        .maxAge(JwtUtil.REFRESH_TOKEN_EXPIRY_SECOND)
        .build();
  }

  public ResponseCookie createAccessTokenCookie(String accessToken) {
    return ResponseCookie.from("accessToken", accessToken)
        .path("/")
        .httpOnly(true)
        .secure(true)
        .sameSite(SameSite.LAX.name())
        .maxAge(JwtUtil.ACCESS_TOKEN_EXPIRY_SECOND)
        .build();
  }
}
