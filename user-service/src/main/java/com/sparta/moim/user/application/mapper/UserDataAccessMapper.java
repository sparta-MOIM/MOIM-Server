package com.sparta.moim.user.application.mapper;

import com.sparta.moim.user.application.dto.ProcessSignupCommand;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {

  public User userFromSignupCommand(ProcessSignupCommand command, String encodedPassword) {
    return User.createUser(command.username(), encodedPassword, command.name());
  }

  public SignupUserResult signupUserResultFromUser(User user) {
    return new SignupUserResult(user.getTrackingId());
  }
}
