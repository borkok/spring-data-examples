/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatamongodb;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.time.LocalDate;

@Document(collection = "books")
@Accessors(fluent=true)
@Getter
@Setter
@ToString
public class Book {
	@Id
	private String id;
	@TextIndexed(weight=2)
	private String title;
	private String author;
	@TextIndexed(weight=1)
	private String description;
	private LocalDate published;
	private Library library;
	@TextScore
	private Float score;

	Book published(String date) {
		this.published = LocalDate.parse(date);
		return this;
	}

	Book library(String name, double lat, double lon) {
		this.library = new Library(name, lat, lon);
		return this;
	}
}
