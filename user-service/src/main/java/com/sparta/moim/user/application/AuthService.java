package com.sparta.moim.user.application;

import static com.sparta.moim.user.application.exception.UserErrorCode.USER_NOT_FOUND;

import com.sparta.moim.common.passport.enums.Passport;
import com.sparta.moim.user.application.exception.UserNotFoundException;
import com.sparta.moim.user.domain.model.User;
import com.sparta.moim.user.domain.repository.UserRepository;
import com.sparta.moim.user.infrastructure.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  @Transactional
  public Passport generatePassport(String accessToken) {
    Claims claims = jwtUtil.extractClaims(accessToken);
    String trackingId = claims.getSubject();
    String username = claims.get("username", String.class);

    User user = userRepository.findByTrackingIdAndDeletedAtIsNull(UUID.fromString(trackingId))
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    return new Passport(trackingId, username, user.getRole().name());
  }
}
