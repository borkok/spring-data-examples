/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdataelastic;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepoQueriesService {
	private final BookRepository bookRepository;

	public RepoQueriesService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<String> findByTitleContainingOrderByPublishedDesc() {
		return bookRepository.findByTitleContainingOrderByPublishedDesc("Programming").stream()
				.map(hit -> hit.getContent().title() + "(" + hit.getScore() + ")")
				.collect(Collectors.toList());
	}

	public List<String> findByPublishedDate() {
		return bookRepository.findByPublishedGreaterThan(LocalDate.parse("2017-10-01")).stream()
				.map(hit -> hit.getContent().published() + "(" + hit.getScore() + ")")
				.collect(Collectors.toList());
	}
}
