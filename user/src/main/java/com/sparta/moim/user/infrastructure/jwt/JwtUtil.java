package com.sparta.moim.user.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
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
        .setSubject(username)
        .claim("role", role)
        .claim("trackingId", trackingId)
        .setIssuer(issuer)
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(expiration)
        .signWith(secretKey)
        .compact();
  }

  public String createRefreshToken(String username, UUID trackingId, ZonedDateTime now) {
    Date expiration = Date.from(now.plusSeconds(REFRESH_TOKEN_EXPIRY_SECOND).toInstant());
    return Jwts.builder()
        .setSubject(username)
        .claim("trackingId", trackingId)
        .setIssuer(issuer)
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(expiration)
        .signWith(secretKey)
        .compact();
  }

  public Claims extractClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
