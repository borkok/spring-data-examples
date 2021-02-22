/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatarest.jpa.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfiguration {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new CustomAuditorAware();
	}
}
