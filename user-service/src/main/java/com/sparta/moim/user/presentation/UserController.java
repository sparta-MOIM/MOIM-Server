package com.sparta.moim.user.presentation;

import static com.sparta.moim.user.application.exception.UserErrorCode.REFRESH_TOKEN_NOT_FOUND;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.user.application.UserService;
import com.sparta.moim.user.application.dto.AccessTokenRefreshResult;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.application.exception.RefreshTokenNotFoundException;
import com.sparta.moim.user.presentation.dto.SignupUserRequest;
import com.sparta.moim.user.presentation.dto.SignupUserResponse;
import com.sparta.moim.user.presentation.mapper.UserPresentationMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserPresentationMapper userPresentationMapper;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponseData<SignupUserResponse>> signup(@RequestBody @Valid SignupUserRequest request) {
    SignupUserResult result = userService.signup(userPresentationMapper.toSignupCommand(request));

    UUID trackingId = result.trackingId();
    URI uri = UriComponentsBuilder.fromUriString("/api/v1/users/{trackingId}")
        .buildAndExpand(trackingId).toUri();
    return ResponseEntity.created(uri).body(ApiResponseData.success(userPresentationMapper.toSignupResult(result)));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponseData<Void>> refreshAccessToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) {
    Cookie refreshToken = WebUtils.getCookie(request, "refreshToken");
    if (refreshToken == null) {
      throw new RefreshTokenNotFoundException(REFRESH_TOKEN_NOT_FOUND);
    }
    AccessTokenRefreshResult result = userService.refreshAccessToken(refreshToken.getValue());
    response.addHeader(HttpHeaders.SET_COOKIE, result.accessTokenCookie().toString());
    return ResponseEntity.ok().body(ApiResponseData.success(null, "액세스 토큰 재발급 완료"));
  }
}
