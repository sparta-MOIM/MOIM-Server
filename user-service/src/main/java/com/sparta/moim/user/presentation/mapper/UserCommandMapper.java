package com.sparta.moim.user.presentation.mapper;

import com.sparta.moim.user.application.dto.ProcessSignupCommand;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.presentation.dto.SignupUserRequest;
import com.sparta.moim.user.presentation.dto.SignupUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserCommandMapper {

  public ProcessSignupCommand toSignupCommand(SignupUserRequest request) {
    return new ProcessSignupCommand(request.username(), request.password(), request.name());
  }

  public SignupUserResponse toSignupResult(SignupUserResult result) {
    return new SignupUserResponse(result.trackingId());
  }
}
