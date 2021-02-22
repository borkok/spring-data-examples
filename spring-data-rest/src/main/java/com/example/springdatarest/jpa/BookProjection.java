/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatarest.jpa;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="simpleBook", types = {Book.class})
public interface BookProjection {
	String getTitle();
}
