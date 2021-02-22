/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdataelastic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.time.LocalDate;

@Document(indexName = "books")
@Accessors(fluent=true)
@Getter
@Setter
@ToString
public class Book {
	@Id
	private String id;
	private String title;
	@Field(type = FieldType.Keyword)
	private String author;
	private String description;
	//without this annotation dates where stored as long
	//and had problem with range query and deserialization
	@Field(type = FieldType.Date, format = DateFormat.basic_date)
	private LocalDate published;
	//without this annotation had problem with geo queries: failed to find geo_point field [library.location]
	@Field(type = FieldType.Nested, includeInParent = true)
	private Library library;

	Book published(String date) {
		this.published = LocalDate.parse(date);
		return this;
	}

	Book library(String name, double lat, double lon) {
		this.library = new Library(name, lat, lon);
		return this;
	}
}
