package com.sparta.moim.user.presentation;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.user.application.UserService;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.presentation.dto.SignupUserRequest;
import com.sparta.moim.user.presentation.dto.SignupUserResponse;
import com.sparta.moim.user.presentation.mapper.UserCommandMapper;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserCommandMapper userCommandMapper;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponseData<SignupUserResponse>> signup(@RequestBody @Valid SignupUserRequest request) {
    SignupUserResult result = userService.signup(userCommandMapper.toSignupCommand(request));

    UUID trackingId = result.trackingId();
    URI uri = UriComponentsBuilder.fromUriString("/api/v1/users/{trackingId}")
        .buildAndExpand(trackingId).toUri();
    return ResponseEntity.created(uri).body(ApiResponseData.success(userCommandMapper.toSignupResult(result)));
  }
}
