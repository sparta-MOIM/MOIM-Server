package com.sparta.moim.gathering.gathering.infrastructure.configuration;

import com.sparta.moim.common.config.JpaConfig;
import com.sparta.moim.common.config.PropertyConfig;
import com.sparta.moim.common.security.GlobalSecurityConfig;
import com.sparta.moim.common.security.filter.GlobalSecurityContextFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    JpaConfig.class,
    PropertyConfig.class,
    GlobalSecurityConfig.class,
    GlobalSecurityContextFilter.class
})
public class GlobalConfig {}
