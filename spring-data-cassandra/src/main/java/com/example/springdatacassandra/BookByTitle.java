package com.example.springdatacassandra;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
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
public class BookByTitle {
	@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private UUID id = UUIDs.timeBased();
	@PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED) //bez tego nie można szukać po tytule
	private String title;
	private String author;
	private String description;
	private LocalDate published;
}
