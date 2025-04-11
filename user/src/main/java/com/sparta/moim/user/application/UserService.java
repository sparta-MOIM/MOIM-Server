package com.sparta.moim.user.application;

import static com.sparta.moim.common.response.Code.ALREADY_EXISTS_USERNAME;

import com.sparta.moim.user.application.dto.ProcessSignupCommand;
import com.sparta.moim.user.application.dto.SignupUserResult;
import com.sparta.moim.user.application.exception.AlreadyExistsUsernameException;
import com.sparta.moim.user.application.mapper.UserDataAccessMapper;
import com.sparta.moim.user.domain.model.User;
import com.sparta.moim.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDataAccessMapper userDataAccessMapper;

  @Transactional
  public SignupUserResult signup(ProcessSignupCommand command) {
    if (userRepository.existsByUsername(command.username())) {
      throw new AlreadyExistsUsernameException(ALREADY_EXISTS_USERNAME);
    }

    String encodedPassword = passwordEncoder.encode(command.password());
    User savedUser = userRepository.save(
        userDataAccessMapper.userToSignupCommand(command, encodedPassword));
    return userDataAccessMapper.signupInfoToUser(savedUser);
  }
}
