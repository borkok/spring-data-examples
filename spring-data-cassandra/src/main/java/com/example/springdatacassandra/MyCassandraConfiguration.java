/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatacassandra;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MyCassandraConfiguration extends AbstractCassandraConfiguration implements BeanClassLoaderAware {

	private String keyspace  = "my_keyspace";

	@Override
	protected String getKeyspaceName() {
		return keyspace;
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

		CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
				.with(KeyspaceOption.DURABLE_WRITES, true)
				.ifNotExists();

		return Arrays.asList(specification);
	}

	@Override
	protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
		return Arrays.asList(DropKeyspaceSpecification.dropKeyspace("my_keyspace"));
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.RECREATE;
		//return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "com.example.springdatacassandra" };
	}
}