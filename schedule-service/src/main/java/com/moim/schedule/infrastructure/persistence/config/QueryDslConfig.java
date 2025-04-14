package com.moim.schedule.infrastructure.persistence.config;

import com.sparta.moim.common.config.JpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaConfig.class)
public class QueryDslConfig {
}
