package com.sparta.moim.user.presentation;

import static com.sparta.moim.user.application.exception.UserErrorCode.REFRESH_TOKEN_NOT_FOUND;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.user.application.UserService;
import com.sparta.moim.user.application.dto.AccessTokenRefreshResult;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.application.dto.UpdateUserResult;
import com.sparta.moim.user.application.dto.UserSummaryQuery;
import com.sparta.moim.user.application.dto.UserSummaryResult;
import com.sparta.moim.user.application.exception.RefreshTokenNotFoundException;
import com.sparta.moim.user.presentation.dto.SignupUserRequest;
import com.sparta.moim.user.presentation.dto.SignupUserResponse;
import com.sparta.moim.user.presentation.dto.UpdateUserRequest;
import com.sparta.moim.user.presentation.dto.UpdateUserResponse;
import com.sparta.moim.user.presentation.mapper.UserPresentationMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
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
    if (refreshToken == null || refreshToken.getValue() == null || refreshToken.getValue().isEmpty()) {
      throw new RefreshTokenNotFoundException(REFRESH_TOKEN_NOT_FOUND);
    }
    AccessTokenRefreshResult result = userService.refreshAccessToken(refreshToken.getValue());
    response.addHeader(HttpHeaders.SET_COOKIE, result.accessTokenCookie().toString());
    return ResponseEntity.ok().body(ApiResponseData.success(null, "액세스 토큰 재발급 완료"));
  }

  @PutMapping("/{trackingId}")
  public ResponseEntity<ApiResponseData<UpdateUserResponse>> updateUser(
      @PathVariable UUID trackingId,
      @RequestBody UpdateUserRequest request
  ) {
    UpdateUserResult result = userService.updateUser(userPresentationMapper.toUpdateCommand(trackingId, request));
    return ResponseEntity.ok().body(
        ApiResponseData.success(userPresentationMapper.toUpdateUserResponse(result), "회원 수정 완료"));
  }

  @DeleteMapping("/{trackingId}")
  public ResponseEntity<ApiResponseData<Void>> deleteUser(
      @PathVariable UUID trackingId
  ) {
    userService.deleteUser(trackingId);
    return ResponseEntity.ok().body(ApiResponseData.success(null, "회원 탈퇴 완료"));
  }

  @GetMapping
  public ResponseEntity<ApiResponseData<Pagination<UserSummaryResult>>> getUserSummary(
      @RequestParam(defaultValue = "1") @Positive(message = "페이지 번호는 1 이상이어야 합니다.") int page,
      @RequestParam(defaultValue = "10") @Positive(message = "페이지 크기는 1 이상이어야 합니다.") int size
  ) {
    UserSummaryQuery query = userPresentationMapper.toUserSummaryQuery(page, size);
    Pagination<UserSummaryResult> result = userService.getUserSummary(query);
    return ResponseEntity.ok(ApiResponseData.success(result));
  }
}
