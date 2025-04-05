package com.sparta.moim.common.utils;

import com.sparta.moim.common.security.CustomUserDetails;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null
			&& authentication.isAuthenticated()
			&& authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
			return Optional.of(userDetails.getUsername());
		} else {
			return Optional.empty();
		}
	}
}