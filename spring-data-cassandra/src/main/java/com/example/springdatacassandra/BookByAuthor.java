package com.example.springdatacassandra;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.data.cassandra.core.mapping.SASI.IndexMode.CONTAINS;

@Table
@Accessors(fluent=true)
@Getter
@Setter
@ToString
public class BookByAuthor {
	@PrimaryKey
	private BookByAuthorKey key;
	private String title;
	private String description;
	private LocalDate published;

	BookByAuthor author(String author) {
		key.author(author);
		return this;
	}
}
