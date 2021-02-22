/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatacassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface BookRepository extends CassandraRepository<Book, Long> {

	List<Book> findByTitle(String title);

	List<Book> findByTitleContaining(String title);
}
