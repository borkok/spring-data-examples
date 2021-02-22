/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdata.jpa.advanced;

import com.example.springdata.jpa.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositoryExtended extends ExtendedJpaRepository<Book, Long> {
}
