package com.example.springdata;

import com.example.springdata.jpa.Book;
import com.example.springdata.jpa.BookRepository;
import com.example.springdata.jpa.advanced.BookRepositoryExtended;
import com.example.springdata.jpa.restricted.BookRestrictedRepository;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;

import java.time.LocalDate;

@Log
@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringDataJpaApplication.class, args);
		//tryOutBookRepository(applicationContext);
		//tryOutExtendedRepository(applicationContext);
		//tryOutRestrictedRepository(applicationContext);
		//tryOutAuditing(applicationContext);
		BookRepository bookRepository = applicationContext.getBean(BookRepository.class);
		ExampleMatcher startingWithMatcher = ExampleMatcher.matching().withMatcher("title", ExampleMatcher.GenericPropertyMatchers.startsWith());
		Example<Book> bookExample = Example.of(new Book("Two", null), startingWithMatcher);
		log.info("Znaleziono by example: " + bookRepository.findAll(bookExample));
	}

	private static void tryOutAuditing(ApplicationContext applicationContext) {
		BookRepository bookRepository = applicationContext.getBean(BookRepository.class);

		Book book = new Book("Java Persistence", LocalDate.parse("2020-12-01"));
		book = bookRepository.save(book);
		log.info("Saved: " + book);

		book.setTitle("More of Java Persistence");
		bookRepository.save(book);

		log.info("Znaleziono by id: " + bookRepository.findById(book.getId()));
	}

	private static void tryOutRestrictedRepository(ApplicationContext applicationContext) {
		BookRestrictedRepository bookRepository = applicationContext.getBean(BookRestrictedRepository.class);
		log.info("Znaleziono by id: " + bookRepository.findById(5L));
	}

	private static void tryOutExtendedRepository(ApplicationContext applicationContext) {
		BookRepositoryExtended bookRepository = applicationContext.getBean(BookRepositoryExtended.class);
		log.info("Znaleziono by ids: " + bookRepository.seekByIds(1L, 2L, 5L).toString());
	}

	private static void tryOutBookRepository(ApplicationContext applicationContext) {
		BookRepository bookRepository = applicationContext.getBean(BookRepository.class);
		Sort sorting = Sort.by("published").ascending().and(Sort.by("id").descending());

		log.info("Znaleziono All: " + bookRepository.findAll().toString());
		log.info("Znaleziono first 3 by published: " + bookRepository.findFirst3ByPublishedAfterOrderByPublished(LocalDate.parse("2020-09-15")).toString());

		log.info("Znaleziono published before: " + bookRepository.seekBooksThatWerePublishedBefore(LocalDate.parse("2020-09-11")).toString());
		log.info("Znaleziono published before sorted: " + bookRepository.seekBooksThatWerePublishedBefore(LocalDate.parse("2020-09-11"), PageRequest.of(0, 3, sorting)).toString());

		log.info("Znaleziono by title derived: " + bookRepository.searchByTitle("Two").toString());
		Page<Book> bookPage = bookRepository.searchByTitle("Two", PageRequest.of(0, 2, sorting));
		log.info("Znaleziono by title derived sorted i Page<T>: " + bookPage.toString());
		log.info("Page<T>: " + bookPage.toList().toString());
		log.info("Page<T> nextPageable: " + bookPage.nextOrLastPageable().toString());

		log.info("Znaleziono by title query: " + bookRepository.findBy("Two").toString());
		log.info("Znaleziono by title query sorted: " + bookRepository.findBy("Two", PageRequest.of(0, 4, sorting)).toString());
	}
}
