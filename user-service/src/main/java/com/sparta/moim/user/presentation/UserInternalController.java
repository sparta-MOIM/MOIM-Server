package com.sparta.moim.user.presentation;

import com.sparta.moim.common.passport.Passport;
import com.sparta.moim.user.application.AuthService;
import com.sparta.moim.user.application.UserService;
import com.sparta.moim.user.application.dto.GetUserResult;
import com.sparta.moim.user.presentation.dto.GetUserResponse;
import com.sparta.moim.user.presentation.mapper.UserPresentationMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/users")
public class UserInternalController {

  private final UserService userService;
  private final AuthService authService;
  private final UserPresentationMapper userPresentationMapper;

  @GetMapping("/{trackingId}")
  public GetUserResponse readUser(@PathVariable UUID trackingId) {
    GetUserResult result = userService.getUser(trackingId);
    return userPresentationMapper.toGetUserResponse(result);
  }

  @GetMapping("/passport")
  public Passport getPassport(@RequestHeader("accessToken") String accessToken) {
    return authService.generatePassport(accessToken);
  }
}
