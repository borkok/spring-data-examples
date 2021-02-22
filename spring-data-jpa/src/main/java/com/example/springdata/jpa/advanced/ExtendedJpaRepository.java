/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdata.jpa.advanced;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ExtendedJpaRepository<T, ID> extends JpaRepository<T, ID> {

	List<T> seekByIds(ID...ids);
}
