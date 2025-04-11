package com.sparta.moim.organization.infrastruct;

import com.sparta.moim.common.security.GlobalSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GlobalSecurityConfig.class)
public class GlobalConfig {
}
