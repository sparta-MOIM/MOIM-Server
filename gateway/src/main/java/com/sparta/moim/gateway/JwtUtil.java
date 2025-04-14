package com.sparta.moim.gateway;

import static com.sparta.moim.gateway.exception.GatewayErrorCode.EXPIRED_JWT;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.JWT_NOT_VALID;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.MALFORMED_JWT;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.SIGNATURE_NOT_VALID;
import static com.sparta.moim.gateway.exception.GatewayErrorCode.UNSUPPORTED_JWT;

import com.sparta.moim.gateway.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private final SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
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
}
