package com.example.springdatacassandra;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.data.cassandra.core.mapping.SASI.IndexMode.CONTAINS;

@Table
@Accessors(fluent=true)
@Getter
@Setter
@ToString
public class Book {
	@PrimaryKey
	private UUID id = UUIDs.timeBased();
	@SASI(indexMode = CONTAINS) //@Indexed lub @SASI, aby można było szukać z LIKE + odpowiedni dla LIKE indexMode (domyślny jest PREFIX)
	private String title;
	private String author;
	private String description;
	private LocalDate published;
	private Library library;

	Book published(String date) {
		this.published = LocalDate.parse(date);
		return this;
	}

	Book library(String name, double lat, double lon) {
		this.library = new Library(name, lat, lon);
		return this;
	}

	BookByTitle convertToBookByTitle() {
		return new BookByTitle()
				.title(title)
				.author(author)
				.description(description)
				.published(published);
	}

	BookByAuthor convertToBookByAuthor() {
		return new BookByAuthor()
				.title(title)
				.author(author)
				.description(description)
				.published(published);
	}
}
