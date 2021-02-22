/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatamongodb;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
	//{"find": "books", "filter": {"title": {"$regularExpression": {"pattern": ".*Programming.*", "options": ""}}}, "sort": {"published": -1}
	List<Book> findByTitleContainingOrderByPublishedDesc(String title);
	//{"find": "books", "filter": {"published": {"$gt": {"$date": "2017-09-30T22:00:00Z"}}}
	List<Book> findByPublishedGreaterThan(LocalDate localDate);

	//{"find":"books", "filter":{"library.location":{"$near":{"x":54.50175034527047,"y":18.538316714853877}}}
	List<Book> findByLibraryLocationNear(Point center);
	// does not sort by score unless explicitly told to
	List<Book> findAllByOrderByScoreDesc(TextCriteria criteria);
    // {"find": "books", "filter": {"author": {"$regularExpression": {"pattern": "Fre\\w*man", "options": ""}}}
	List<Book> findByAuthorRegex(String regex);

	@Query("{ 'title' : ?0 }")
	List<Book> seekByTitle(String title);
}
