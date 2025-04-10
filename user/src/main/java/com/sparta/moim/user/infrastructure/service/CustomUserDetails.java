package com.sparta.moim.user.infrastructure.service;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

  private final String username;
  private final String role;
  private final UUID trackingId;
  private final String password;

  public CustomUserDetails(String username, String role, UUID trackingId, String password) {
    this.username = username;
    this.role = role;
    this.trackingId = trackingId;
    this.password = password;
  }

  // 필수 메서드 구현
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
  }

  // 사용하지 않음
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}