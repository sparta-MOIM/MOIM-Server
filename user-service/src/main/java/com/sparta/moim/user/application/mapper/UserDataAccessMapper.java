package com.sparta.moim.user.application.mapper;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.user.application.dto.GetUserResult;
import com.sparta.moim.user.application.dto.ProcessSignupCommand;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.application.dto.UpdateUserResult;
import com.sparta.moim.user.application.dto.UserSummaryResult;
import com.sparta.moim.user.domain.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {

  public User userFromSignupCommand(ProcessSignupCommand command, String encodedPassword) {
    return User.createUser(command.username(), encodedPassword, command.name());
  }

  public SignupUserResult signupUserResultFromUser(User user) {
    return new SignupUserResult(user.getTrackingId());
  }

  public GetUserResult getUserResultFromUser(User findUser) {
    return new GetUserResult(findUser.getTrackingId(), findUser.getUsername(), findUser.getName());
  }

  public UpdateUserResult updateUserResultFromUser(User user) {
    return new UpdateUserResult(user.getTrackingId(), user.getName());
  }

  public Pagination<UserSummaryResult> paginationFromUser(Page<User> userPage) {
    List<UserSummaryResult> result = userPage.stream().map(this::userSummaryFromUser).toList();
    return Pagination.of(
        userPage.getNumber() + 1,
        userPage.getSize(),
        userPage.getTotalElements(),
        result
    );
  }

  private UserSummaryResult userSummaryFromUser(User user) {
    return new UserSummaryResult(user.getTrackingId(), user.getUsername(), user.getName());
  }
}
