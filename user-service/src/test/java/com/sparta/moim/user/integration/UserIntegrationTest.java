package com.sparta.moim.user.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.user.domain.model.User;
import com.sparta.moim.user.domain.UserRepository;
import com.sparta.moim.user.presentation.dto.LoginRequest;
import com.sparta.moim.user.presentation.dto.SignupUserRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

public class UserIntegrationTest extends IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setup() {
    userRepository.deleteAll();
  }

  @Nested
  class Signup {

    String SIGNUP_URL = "/api/v1/users/signup";

    @Test
    @DisplayName("이미 존재하는 username으로 가입 시 실패한다.")
    void existsUsername() throws Exception {
      String username = "username";
      String password = "1q2w3e4rR!";
      String name = "홍길동";
      SignupUserRequest request = new SignupUserRequest(username, password, name);

      userRepository.save(User.createUser(username, passwordEncoder.encode(password), "홍길동"));

      mockMvc.perform(post(SIGNUP_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.code").value("U001"))
          .andExpect(jsonPath("$.message").value("이미 사용 중인 username 입니다."))
          .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("성공")
    void success() throws Exception {
      String username = "username";
      String password = "1q2w3e4rR!";
      String name = "홍길동";
      SignupUserRequest request = new SignupUserRequest(username, password, name);

      mockMvc.perform(post(SIGNUP_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isCreated())
          .andExpect(header().exists("Location"));
    }
  }

  @Nested
  class Login {

    String LOGIN_URL = "/api/v1/users/login";

    @Test
    @DisplayName("성공")
    void success() throws Exception {
      String username = "username";
      String password = "1q2w3e4rR!";
      LoginRequest request = new LoginRequest(username, password);

      userRepository.save(User.createUser(username, passwordEncoder.encode(password), "홍길동"));

      mockMvc.perform(post(LOGIN_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andExpect(result -> {
            Cookie accessToken = result.getResponse().getCookie("accessToken");
            assertNotNull(accessToken);
            assertThat(accessToken.isHttpOnly()).isTrue();
            assertThat(accessToken.getSecure()).isTrue();

            Cookie refreshToken = result.getResponse().getCookie("refreshToken");
            assertNotNull(refreshToken);
            assertThat(refreshToken.isHttpOnly()).isTrue();
            assertThat(refreshToken.getSecure()).isTrue();
          });
    }
  }
}
