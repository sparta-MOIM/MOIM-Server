package com.sparta.moim.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.security.filter.GlobalSecurityContextFilter;
import com.sparta.moim.common.security.handler.ForbiddenHandler;
import com.sparta.moim.common.security.handler.UnauthorizedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class GlobalSecurityConfig {

  private final ObjectMapper objectMapper;
  private final GlobalSecurityContextFilter globalSecurityContextFilter;

  @Bean
  public SecurityFilterChain globalSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)  // 로그인 페이지 비활성화
        .httpBasic(AbstractHttpConfigurer::disable)  // HTTP 기본 인증 비활성화
        .logout(AbstractHttpConfigurer::disable)  // 로그아웃 기능 비활성화
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((unauthorizedHandler())) // 인증 실패 시 401 반환
            .accessDeniedHandler(forbiddenHandler())) // 권한 없을 시 403 반환
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(globalSecurityContextFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationEntryPoint unauthorizedHandler() {
    return new UnauthorizedHandler(objectMapper);
  }

  @Bean
  public AccessDeniedHandler forbiddenHandler() {
    return new ForbiddenHandler(objectMapper);
  }
}