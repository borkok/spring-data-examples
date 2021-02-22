/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatamongodb;

import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoSpatialQueriesService {
	private final BookRepository bookRepository;

	public GeoSpatialQueriesService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// using repository for queries
	public List<String> findBooksNearGdynia() {
		return bookRepository.findByLibraryLocationNear(new Point(54.50175034527047, 18.538316714853877)).stream()
				.map(book -> book.library().city())
				.collect(Collectors.toList());
	}
}
