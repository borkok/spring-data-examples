package com.example.springdatamongodb;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegexService {
	private final BookRepository bookRepository;

	public RegexService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<String> findByAuthorRegex() {
		return bookRepository.findByAuthorRegex("Fre\\w*man").stream()
				.map(Book::author)
				.collect(Collectors.toList());
	}
}
