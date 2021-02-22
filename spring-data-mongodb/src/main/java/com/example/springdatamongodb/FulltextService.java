/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatamongodb;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FulltextService {
	private final BookRepository bookRepository;

	public FulltextService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<String> findByText(String word) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(word);
		return bookRepository.findAllByOrderByScoreDesc(criteria).stream()
				.map(book -> book.title() + " (" + book.score() + ")")
				.collect(Collectors.toList());
	}
}
