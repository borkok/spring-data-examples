/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatamongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

/*
Simple class for MongoOperations interface presentation
 */
public class MongoTemplateExample {

	private final MongoOperations mongoOperations;

	public MongoTemplateExample(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
		//mongoOperations.
	}
}
