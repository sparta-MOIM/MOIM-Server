package com.sparta.moim.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.LogoutSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GlobalSecurityConfig {

  @Bean
  public SecurityWebFilterChain globalSecurityFilterChain(ServerHttpSecurity http) throws Exception {
    http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .httpBasic(HttpBasicSpec::disable)
        .formLogin(FormLoginSpec::disable)
        .logout(LogoutSpec::disable);
    return http.build();
  }
}