package com.sparta.moim.user.infrastructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.user.presentation.dto.LoginRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper;

  public LoginFilter(AntPathRequestMatcher matcher, AuthenticationManager authenticationManager,
      ObjectMapper objectMapper) {
    super(matcher, authenticationManager);
    this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    log.info("로그인 시도");
    LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
    String username = loginRequest.username();
    String password = loginRequest.password();

    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken
        .unauthenticated(username, password);

    return this.getAuthenticationManager().authenticate(authRequest);
  }
}
