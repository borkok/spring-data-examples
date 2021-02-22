/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdata.jpa.auditing;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CustomAuditorAware implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("Kinga");
	}
}
