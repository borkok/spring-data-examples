package com.example.springdatamongodb;

import org.springframework.data.mongodb.core.MongoOperations;
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
				.map(Book::title)
				.collect(Collectors.toList());
	}

	public List<String> findByPublishedDate() {
		return bookRepository.findByPublishedGreaterThan(LocalDate.parse("2017-10-01")).stream()
				.map(book -> ""+book.published())
				.collect(Collectors.toList());
	}

	public List<String> findByTitleJsonQuery() {
		return bookRepository.seekByTitle("Functional Programming in Scala").stream()
				.map(Book::toString)
				.collect(Collectors.toList());
	}
}
