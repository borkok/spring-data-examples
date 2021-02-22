/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatacassandra;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;

@Service
public class CassandraService {

	private final CassandraOperations cassandraOperations;

	public CassandraService(CassandraOperations cassandraOperations) {
		this.cassandraOperations = cassandraOperations;
	}
}
