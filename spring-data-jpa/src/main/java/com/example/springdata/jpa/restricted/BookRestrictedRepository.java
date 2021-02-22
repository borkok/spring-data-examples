/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdata.jpa.restricted;

import com.example.springdata.jpa.Book;

import java.util.List;

public interface BookRestrictedRepository extends RestrictedRepository<Book, Long> {

	List<Book> findFirstByTitle(String title);
}
