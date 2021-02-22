/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdata.jpa.advanced;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.springdata.jpa",
		repositoryBaseClass = ExtendedJpaRepositoryImpl.class)
public class BookJpaConfiguration {
}
