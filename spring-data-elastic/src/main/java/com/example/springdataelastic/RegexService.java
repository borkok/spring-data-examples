package com.example.springdataelastic;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegexService {
	private final ElasticsearchOperations elasticsearchOperations;

	public RegexService(ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	// To match a term, the regular expression must match the entire string.
	// But if it is not a keyword field, text will be split and lowercased - so expression will match any entire word or not match at all
	// Best make author field type keyword
	//{"regexp":{"author":{"value":".*Fre.*man.*","flags_value":65535,"max_determinized_states":10000,"boost":1.0}}}
	public List<String> findByAuthorRegex() {
		Query query = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.regexpQuery("author", ".*Fre.*man.*"))
				.build();

		SearchHits<Book> searchHits = elasticsearchOperations.search(query, Book.class);
		return searchHits.getSearchHits().stream()
				.map(this::printHit)
				.collect(Collectors.toList());
	}

	private String printHit(SearchHit<Book> hit) {
		Book book = hit.getContent();
		return book.author() + " (" + hit.getScore() + ")";
	}
}
