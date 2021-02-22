package com.example.springdataelastic;

import lombok.extern.java.Log;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@SpringBootApplication
public class SpringDataElasticApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringDataElasticApplication.class, args);
		BookRepository bookRepository = applicationContext.getBean(BookRepository.class);

		seed(bookRepository);

		//geo(applicationContext);
		//simple(applicationContext);
		fulltext(applicationContext);
		//queryVsFilter(applicationContext);

		//regex(applicationContext);
	}

	private static void regex(ConfigurableApplicationContext applicationContext) {
		RegexService regexService = applicationContext.getBean(RegexService.class);
		log.info("Found by regex: " + regexService.findByAuthorRegex());
	}

	private static void queryVsFilter(ConfigurableApplicationContext applicationContext) {
		QueryAndFilterService queryAndFilterService = applicationContext.getBean(QueryAndFilterService.class);
		log.info("Found by two queries: " + queryAndFilterService.queryOnly());
		log.info("Found by query and filter: " + queryAndFilterService.queryAndFilter());
	}

	private static void fulltext(ConfigurableApplicationContext applicationContext) {
		FulltextService fulltextService = applicationContext.getBean(FulltextService.class);
		String term = "programming";
		log.info("Found by criteria <is> '"+ term +"': " + fulltextService.findByText(term));

		//both search results are the same, no difference
		Stream.of("programming", "prAgramming", "programing", "programmnig", "aprograming", "prAgramnig")
				.peek(word -> log.info("Found by criteria <fuzzy> '"+ word +"': " + fulltextService.findByFuzzyText(word)))
				.forEach(word -> log.info("Found by native <multiMatch> with fuzziness 2 '"+ word + "': " + fulltextService.findByFuzzyNative(word)));
	}

	private static void simple(ConfigurableApplicationContext applicationContext) {
		RepoQueriesService repoQueriesService = applicationContext.getBean(RepoQueriesService.class);
		log.info("Found by title and sorted: " + repoQueriesService.findByTitleContainingOrderByPublishedDesc());
		log.info("Found by published date: " + repoQueriesService.findByPublishedDate());
	}

	private static void geo(ConfigurableApplicationContext applicationContext) {
		GeoSpatialQueriesService geoSpatialQueriesService = applicationContext.getBean(GeoSpatialQueriesService.class);
		log.info("Found near Gdynia: " + geoSpatialQueriesService.findBooksNearGdynia());
	}

	private static void seed(BookRepository bookRepository) {
		bookRepository.deleteAll();
		bookRepository.saveAll(List.of(
				new Book()
						.title("Functional Programming in Scala")
						.author("Paul Chiusano")
						.description("Functional Programming in Scala is a serious tutorial for programmers looking to learn FP and apply it to the everyday business of coding. The book guides readers from basic techniques to advanced topics in a logical, concise, and clear progression. In it, you'll find concrete examples and exercises that open up the world of functional programming.")
						.published("2017-10-12")
						.library("Longyearbyen", 78.21, 15.55),
				new Book()
						.title("Functional Programming in JavaScript")
						.author("Luis Atencio")
						.description("Functional Programming in JavaScript teaches JavaScript developers functional techniques that will improve extensibility, modularity, reusability, testability, and performance. Through concrete examples and jargon-free explanations, this book teaches you how to apply functional programming to real-life development tasks")
						.published("2018-02-22")
						.library("Warszawa", 52.23,21.01),
				new Book()
						.title("Unit Testing Principles, Practices and Patterns")
						.author("Vladimir Khorikov")
						.description("Unit Testing Principles, Practices, and Patterns: Effective testing styles, patterns, and reliable automation for unit testing, mocking, and integration testing with examples in C#")
						.published("2020-04-03")
						.library("Moscow",55.755833,37.617222),
				new Book()
						.title("Seven Languages in Seven Weeks")
						.author("Bruce Freyoman Tate")
						.description("You should learn a programming language every year, as recommended by The Pragmatic Programmer. But if one per year is good, how about Seven Languages in Seven Weeks? In this book you'll get a hands-on tour of Clojure, Haskell, Io, Prolog, Scala, Erlang, and Ruby.")
						.published("2010-10-01")
						.library("New York", 40.71274, -74.005974),
				new Book()
						.title("OCP Oracle Certified Professional Java SE 11 Developer Complete Study Guide")
						.author("Scott Freeman")
						.description("The most comprehensive prep guide available for new Oracle Certified Professional Java SE11 Developer certifications―covers the new 1Z0-819 Exam, the Programmer I and II Exams 1Z0-815 and 1Z0-816, and the Upgrade Exam 1Z0-817!")
						.published("2020-08-02")
						.library("New Delhi", 28.613889,77.208889),
				new Book()
						.title("Growing Object-Oriented Software, Guided by Tests")
						.author("Steve Freeman")
						.description("Steve Freeman and Nat Pryce describe the processes they use, the design principles they strive to achieve, and some of the tools that help them get the job done. Through an extended worked example, you’ll learn how TDD works at multiple levels, using tests to drive the features and the object-oriented structure of the code, and using Mock Objects to discover and then describe relationships between objects.")
						.published("2009-10-30")
						.library("Rio de Janeiro", -22.91,-43.20)
		));
	}

	/*
	OTHER EXAMPLES specific for ES
	 */
	private static void elasticSearchOperationsNativeQueryExample(ElasticsearchOperations elasticsearchOperations) {
		QueryBuilders.matchQuery("title", "languages test").fuzziness(Fuzziness.ONE);
		QueryBuilders.termQuery("published", "20171012");

		GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders.geoDistanceQuery("library.location")
				//.point(69.44, 22.05) // NO
				.point(74.45, 19.06) // YES
				.distance(600, DistanceUnit.KILOMETERS);
		QueryBuilder theQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchQuery("title", "programmin").fuzziness(Fuzziness.ONE))
				//.must(QueryBuilders.rangeQuery("published").gt("20180122"))
				//.filter(geoDistanceQueryBuilder)
				;

		Query query = new NativeSearchQueryBuilder()
				.withQuery(theQuery)
				.withFilter(geoDistanceQueryBuilder)
				.build();
		List<Book> books = elasticsearchOperations.search(query, Book.class)
				.getSearchHits().stream()
				.map(SearchHit::getContent)
				.collect(Collectors.toList());
		log.info("Books found with native query: " + books);
	}

	private static void elasticSearchOperationsStringQueryExample(ElasticsearchOperations elasticsearchOperations) {
		Query query = new StringQuery("{\n" +
				"        \"match\": {\n" +
				"            \"title\": {\n" +
				"                \"query\": \"Seven Languages in Seven Weaks\",\n" +
				"                \"operator\": \"and\",\n" +
				"                \"fuzziness\": \"auto\"\n" +
				"            }\n" +
				"        }\n" +
				"}");
		List<Book> books = elasticsearchOperations.search(query, Book.class)
				.getSearchHits().stream()
				.map(SearchHit::getContent)
				.collect(Collectors.toList());
		log.info("Books with fuzzy title search: " + books);
	}


}
