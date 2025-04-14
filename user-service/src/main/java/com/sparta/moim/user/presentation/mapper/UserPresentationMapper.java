package com.sparta.moim.user.presentation.mapper;

import com.sparta.moim.user.application.dto.GetUserResult;
import com.sparta.moim.user.application.dto.ProcessSignupCommand;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.presentation.dto.GetUserResponse;
import com.sparta.moim.user.presentation.dto.SignupUserRequest;
import com.sparta.moim.user.presentation.dto.SignupUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserPresentationMapper {

  public ProcessSignupCommand toSignupCommand(SignupUserRequest request) {
    return new ProcessSignupCommand(request.username(), request.password(), request.name());
  }

  public SignupUserResponse toSignupResult(SignupUserResult result) {
    return new SignupUserResponse(result.trackingId());
  }

  public GetUserResponse toGetUserResponse(GetUserResult result) {
    return new GetUserResponse(result.trackingId(), result.username(), result.name());
  }
}
