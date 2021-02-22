/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdata.jpa.restricted;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface RestrictedRepository<T, ID> extends Repository<T, ID> {
	<S extends T> S save(S s);

	Optional<T> findById(ID aId);

	long count();
}
