/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatacassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface BookByAuthorRepository extends CassandraRepository<BookByAuthor, Long> {

	List<BookByAuthor> findFirstByKeyAuthor(String author);
}
