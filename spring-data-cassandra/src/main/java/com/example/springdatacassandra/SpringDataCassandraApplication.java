package com.example.springdatacassandra;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

@Log
@SpringBootApplication
public class SpringDataCassandraApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringDataCassandraApplication.class, args);

		seed(applicationContext);

		BookRepository bookRepository = applicationContext.getBean(BookRepository.class);
		List<Book> books = bookRepository.findByTitleContaining("Seven");
		log.info("Found by title like: " + books);

		BookByTitleRepository bookByTitleRepository = applicationContext.getBean(BookByTitleRepository.class);
		List<BookByTitle> booksByTitle = bookByTitleRepository.findByTitle("Seven Languages in Seven Weeks");
		log.info("Found by title: " + booksByTitle);

		BookByAuthorRepository bookByAuthorRepository = applicationContext.getBean(BookByAuthorRepository.class);
		List<BookByAuthor> booksByAuthor = bookByAuthorRepository.findFirstByKeyAuthor("Vladimir Khorikov");
		log.info("Found by author: " + booksByAuthor);
	}

	private static void seed(ConfigurableApplicationContext applicationContext) {
		BookRepository bookRepository = applicationContext.getBean(BookRepository.class);
		bookRepository.deleteAll();
		List<Book> books = List.of(
				new Book()
						.title("Functional Programming in Scala")
						.author("Paul Chiusano")
						.description("Functional Programming in Scala is a serious tutorial for programmers looking to learn FP and apply it to the everyday business of coding. The book guides readers from basic techniques to advanced topics in a logical, concise, and clear progression. In it, you'll find concrete examples and exercises that open up the world of functional programming.")
						.published("2017-10-12")
						.library("Longyearbyen", 78.21, 15.55)
				, new Book()
						.title("Functional Programming in JavaScript")
						.author("Luis Freyoman Atencio")
						.description("Functional Programming in JavaScript teaches JavaScript developers functional techniques that will improve extensibility, modularity, reusability, testability, and performance. Through concrete examples and jargon-free explanations, this book teaches you how to apply functional programming to real-life development tasks")
						.published("2018-02-22")
						.library("Warszawa", 52.23, 21.01)
				, new Book()
						.title("Unit Testing Principles, Practices and Patterns")
						.author("Vladimir Khorikov")
						.description("Unit Testing Principles, Practices, and Patterns: Effective testing styles, patterns, and reliable automation for unit testing, mocking, and integration testing with examples in C#")
						.published("2020-04-03")
						.library("Moscow", 55.755833, 37.617222)
				, new Book()
						.title("Seven Languages in Seven Weeks")
						.author("Bruce Tate")
						.description("You should learn a programming language every year, as recommended by The Pragmatic Programmer. But if one per year is good, how about Seven Languages in Seven Weeks? In this book you'll get a hands-on tour of Clojure, Haskell, Io, Prolog, Scala, Erlang, and Ruby.")
						.published("2010-10-01")
						.library("New York", 40.71274, -74.005974)
				, new Book()
						.title("OCP Oracle Certified Professional Java SE 11 Developer Complete Study Guide")
						.author("Scott Freeman")
						.description("The most comprehensive prep guide available for new Oracle Certified Professional Java SE11 Developer certifications―covers the new 1Z0-819 Exam, the Programmer I and II Exams 1Z0-815 and 1Z0-816, and the Upgrade Exam 1Z0-817!")
						.published("2020-08-02")
						.library("New Delhi", 28.613889, 77.208889)
				, new Book()
						.title("Growing Object-Oriented Software, Guided by Tests")
						.author("Steve Freeman")
						.description("Steve Freeman and Nat Pryce describe the processes they use, the design principles they strive to achieve, and some of the tools that help them get the job done. Through an extended worked example, you’ll learn how TDD works at multiple levels, using tests to drive the features and the object-oriented structure of the code, and using Mock Objects to discover and then describe relationships between objects.")
						.published("2009-10-30")
						.library("Rio de Janeiro", -22.91, -43.20)
		);
		bookRepository.saveAll(books);

		BookByTitleRepository bookByTitleRepository = applicationContext.getBean(BookByTitleRepository.class);
		bookByTitleRepository.saveAll(books.stream()
				.map(Book::convertToBookByTitle)
				.collect(Collectors.toList()));

		BookByAuthorRepository bookByAuthorRepository = applicationContext.getBean(BookByAuthorRepository.class);
		bookByAuthorRepository.saveAll(books.stream()
				.map(Book::convertToBookByAuthor)
				.collect(Collectors.toList()));
	}

}
