package com.sparta.moim.common.security.filter;

import static com.sparta.moim.common.passport.enums.UserPassportConstants.X_USER_ID;
import static com.sparta.moim.common.passport.enums.UserPassportConstants.X_USER_NAME;
import static com.sparta.moim.common.passport.enums.UserPassportConstants.X_USER_ROLE;

import com.sparta.moim.common.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class GlobalSecurityContextFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String username = request.getHeader(X_USER_NAME.getValue());
    String role = request.getHeader(X_USER_ROLE.getValue());
    String userId = request.getHeader(X_USER_ID.getValue());

    if (username != null && role != null && userId != null) {
      log.info("X-header: username={}, role={}, userId={}", username, role, userId);

      UUID id = UUID.fromString(userId);
      CustomUserDetails customUserDetails = new CustomUserDetails(username, role, id);
      Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
          customUserDetails, null, customUserDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("Authentication authorities: {}", authentication.getAuthorities());
    }
    filterChain.doFilter(request, response);
  }
}