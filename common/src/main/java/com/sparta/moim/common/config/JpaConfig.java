package com.sparta.moim.common.config;

import com.sparta.moim.common.utils.UserAuditorAware;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@ConditionalOnClass(name = "jakarta.persistence.EntityManager")
public class JpaConfig {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new UserAuditorAware();
	}

//	@Bean
//	JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
//		return new JPAQueryFactory(entityManager);
//	}

}
