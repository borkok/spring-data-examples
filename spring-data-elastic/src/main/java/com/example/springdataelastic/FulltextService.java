/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdataelastic;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FulltextService {
	private final ElasticsearchOperations elasticsearchOperations;

	public FulltextService(ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	//{"bool":{"should":[{"query_string":{"query":"programming","fields":["title^1.0"],"type":"best_fields","default_operator":"and","max_determinized_states":10000,"enable_position_increments":true,"fuzziness":"AUTO","fuzzy_prefix_length":0,"fuzzy_max_expansions":50,"phrase_slop":0,"escape":false,"auto_generate_synonyms_phrase_query":true,"fuzzy_transpositions":true,"boost":2.0}},{"query_string":{"query":"programming","fields":["description^1.0"],"type":"best_fields","default_operator":"and","max_determinized_states":10000,"enable_position_increments":true,"fuzziness":"AUTO","fuzzy_prefix_length":0,"fuzzy_max_expansions":50,"phrase_slop":0,"escape":false,"auto_generate_synonyms_phrase_query":true,"fuzzy_transpositions":true,"boost":1.0}}],"adjust_pure_negative":true,"boost":1.0}},"post_filter":{"bool":{"adjust_pure_negative":true,"boost":1.0}}
	public List<String> findByText(String term) {
		Criteria criteria = new Criteria("title").boost(2).is(term).or("description").boost(1).is(term);
		Query query = new CriteriaQuery(criteria);
		SearchHits<Book> searchHits = elasticsearchOperations.search(query, Book.class);
		return searchHits.getSearchHits().stream()
				.map(this::printHit)
				.collect(Collectors.toList());
	}

	//{"bool":{"should":[{"fuzzy":{"title":{"value":"programnig","fuzziness":"AUTO","prefix_length":0,"max_expansions":50,"transpositions":true,"boost":2.0}}},{"fuzzy":{"description":{"value":"programnig","fuzziness":"AUTO","prefix_length":0,"max_expansions":50,"transpositions":true,"boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}},"post_filter":{"bool":{"adjust_pure_negative":true,"boost":1.0}}
	public List<String> findByFuzzyText(String term) {
		Criteria criteria = new Criteria("title").boost(2).fuzzy(term).or("description").boost(1).fuzzy(term);
		Query query = new CriteriaQuery(criteria);
		SearchHits<Book> searchHits = elasticsearchOperations.search(query, Book.class);
		return searchHits.getSearchHits().stream()
				.map(this::printHit)
				.collect(Collectors.toList());
	}

	//{"multi_match":{"query":"prAgraming","fields":["description^1.0","title^2.0"],"type":"best_fields","operator":"OR","slop":0,"fuzziness":"2","prefix_length":0,"max_expansions":50,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"fuzzy_transpositions":true,"boost":1.0}},"version":true}"
	public List<String> findByFuzzyNative(String term) {
		Query query = new NativeSearchQueryBuilder().withQuery(
				QueryBuilders.multiMatchQuery(term)
						.field("title", 2)
						.field("description", 1)
						.fuzziness(Fuzziness.TWO)
		).build();

		SearchHits<Book> searchHits = elasticsearchOperations.search(query, Book.class);
		return searchHits.getSearchHits().stream()
				.map(this::printHit)
				.collect(Collectors.toList());
	}

	private String printHit(SearchHit<Book> hit) {
		Book book = hit.getContent();
		return book.title() + " (" + hit.getScore() + ")";
	}
}
