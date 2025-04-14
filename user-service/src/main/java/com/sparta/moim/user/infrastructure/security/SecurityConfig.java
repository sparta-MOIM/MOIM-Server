package com.sparta.moim.user.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moim.common.security.filter.GlobalSecurityContextFilter;
import com.sparta.moim.user.domain.repository.UserRepository;
import com.sparta.moim.user.infrastructure.jwt.JwtUtil;
import com.sparta.moim.user.infrastructure.security.filter.LoginFilter;
import com.sparta.moim.user.infrastructure.security.handler.CustomLogoutSuccessHandler;
import com.sparta.moim.user.infrastructure.security.handler.LoginFailureHandler;
import com.sparta.moim.user.infrastructure.security.handler.LoginSuccessHandler;
import com.sparta.moim.user.infrastructure.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final ObjectMapper objectMapper;
  private final JwtUtil jwtUtil;
  private final GlobalSecurityContextFilter globalSecurityContextFilter;
  private final UserRepository userRepository;

  private final AntPathRequestMatcher loginMatcher = new AntPathRequestMatcher(
      "/api/v1/users/login", "POST");

  private final AntPathRequestMatcher logoutMatcher = new AntPathRequestMatcher(
      "/api/v1/users/logout", "POST");

  @Bean
  public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/**")
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)  // 로그인 페이지 비활성화
        .httpBasic(AbstractHttpConfigurer::disable)  // HTTP 기본 인증 비활성화
        .logout(
            (configurer) -> configurer
                .logoutRequestMatcher(logoutMatcher)
                .deleteCookies("accessToken", "refreshToken")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler()))
        .authorizeHttpRequests(auth -> auth.requestMatchers(
            "/api/v1/users/**", "/api/v1/auth/**", "/internal/v1/users/**").permitAll().anyRequest().authenticated())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(globalSecurityContextFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService(userRepository);
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(daoAuthenticationProvider);
  }

  @Bean
  public LoginFilter loginFilter() {
    LoginFilter loginFilter = new LoginFilter(loginMatcher, authenticationManager(), objectMapper);
    loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(jwtUtil));
    loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler(objectMapper));
    return loginFilter;
  }
}