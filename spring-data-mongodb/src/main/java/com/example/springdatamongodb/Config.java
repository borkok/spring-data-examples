/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatamongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/*
   Needed to turn on auto index creation for testing
 */
@Configuration
public class Config extends AbstractMongoClientConfiguration {

	@Override
	protected String getDatabaseName() {
		return "mongo-db";
	}

	//	Auto index creation is disabled by default and needs to be enabled through the configuration (see Index Creation).
	// Produkcyjnie - we generally recommend explicit index creation
	@Override
	public boolean autoIndexCreation() {
		return true;
	}
}
