package com.sparta.moim.user.application;

import static com.sparta.moim.user.application.exception.UserErrorCode.ALREADY_EXISTS_USERNAME;
import static com.sparta.moim.user.application.exception.UserErrorCode.USER_NOT_FOUND;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.user.application.dto.AccessTokenRefreshResult;
import com.sparta.moim.user.application.dto.GetUserResult;
import com.sparta.moim.user.application.dto.ProcessSignupCommand;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.application.dto.UpdateUserCommand;
import com.sparta.moim.user.application.dto.UpdateUserResult;
import com.sparta.moim.user.application.dto.UserSummaryResult;
import com.sparta.moim.user.application.dto.UserSummaryQuery;
import com.sparta.moim.user.application.exception.AlreadyExistsUsernameException;
import com.sparta.moim.user.application.exception.UserNotFoundException;
import com.sparta.moim.user.application.mapper.UserDataAccessMapper;
import com.sparta.moim.user.domain.model.User;
import com.sparta.moim.user.domain.repository.UserRepository;
import com.sparta.moim.user.infrastructure.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDataAccessMapper userDataAccessMapper;
  private final JwtUtil jwtUtil;

  @Transactional
  public SignupUserResult signup(ProcessSignupCommand command) {
    if (userRepository.existsByUsername(command.username())) {
      throw new AlreadyExistsUsernameException(ALREADY_EXISTS_USERNAME);
    }

    String encodedPassword = passwordEncoder.encode(command.password());
    User savedUser = userRepository.save(
        userDataAccessMapper.userFromSignupCommand(command, encodedPassword));
    return userDataAccessMapper.signupUserResultFromUser(savedUser);
  }

  @Transactional(readOnly = true)
  public GetUserResult getUser(UUID trackingId) {
    User findUser = userRepository.findByTrackingIdAndDeletedAtIsNull(trackingId)
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    return userDataAccessMapper.getUserResultFromUser(findUser);
  }

  @Transactional
  public AccessTokenRefreshResult refreshAccessToken(String refreshToken) {
    Claims claims = jwtUtil.extractClaims(refreshToken);

    User user = userRepository.findByTrackingIdAndDeletedAtIsNull(UUID.fromString(claims.getSubject()))
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    String username = user.getUsername();
    String role = user.getRole().name();
    UUID trackingId = user.getTrackingId();

    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    String accessToken = jwtUtil.createAccessToken(username, role, trackingId, now);
    ResponseCookie accessTokenCookie = jwtUtil.createAccessTokenCookie(accessToken);

    return new AccessTokenRefreshResult(accessTokenCookie);
  }

  @Transactional
  public UpdateUserResult updateUser(UpdateUserCommand command) {
    User user = userRepository.findByTrackingIdAndDeletedAtIsNull(command.trackingId())
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    user.updateUser(command.name());
    return userDataAccessMapper.updateUserResultFromUser(user);
  }

  @Transactional
  public void deleteUser(UUID trackingId) {
    User user = userRepository.findByTrackingIdAndDeletedAtIsNull(trackingId)
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    user.softDelete(user.getUsername());
  }

  @Transactional(readOnly = true)
  public Pagination<UserSummaryResult> getUserSummary(UserSummaryQuery query) {
    Pageable pageable = PageRequest.of(query.page() - 1, query.size());
    Page<User> userPage = userRepository.findAllByDeletedAtIsNull(pageable);
    return userDataAccessMapper.paginationFromUser(userPage);
  }
}
