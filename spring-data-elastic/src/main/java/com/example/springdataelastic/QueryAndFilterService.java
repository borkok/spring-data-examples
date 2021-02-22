/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdataelastic;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//specific to ES
@Service
public class QueryAndFilterService {
	private final ElasticsearchOperations elasticsearchOperations;
	private final String term = "programmer";

	public QueryAndFilterService(ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	//{"bool":{"must":[{"multi_match":{"query":"programmer","fields":["description^1.0","title^2.0"],"type":"best_fields","operator":"OR","slop":0,"fuzziness":"2","prefix_length":0,"max_expansions":50,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"fuzzy_transpositions":true,"boost":1.0}},{"range":{"published":{"from":"20171001","to":null,"include_lower":false,"include_upper":true,"boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}}
	public List<String> queryOnly() {
		Query query = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.boolQuery().must(createFuzzyQuery(term)).must(createRangeQuery()))
				.build();

		SearchHits<Book> searchHits = elasticsearchOperations.search(query, Book.class);
		return searchHits.getSearchHits().stream()
				.map(this::printHit)
				.collect(Collectors.toList());
	}

	//{"multi_match":{"query":"programmer","fields":["description^1.0","title^2.0"],"type":"best_fields","operator":"OR","slop":0,"fuzziness":"2","prefix_length":0,"max_expansions":50,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"fuzzy_transpositions":true,"boost":1.0}},"post_filter":{"range":{"published":{"from":"20171001","to":null,"include_lower":false,"include_upper":true,"boost":1.0}}}
	public List<String> queryAndFilter() {
		Query query = new NativeSearchQueryBuilder()
				.withQuery(createFuzzyQuery(term))
				.withFilter(createRangeQuery())
				.build();

		SearchHits<Book> searchHits = elasticsearchOperations.search(query, Book.class);
		return searchHits.getSearchHits().stream()
				.map(this::printHit)
				.collect(Collectors.toList());
	}

	private RangeQueryBuilder createRangeQuery() {
		return QueryBuilders.rangeQuery("published").gt("20171001");
	}

	private MultiMatchQueryBuilder createFuzzyQuery(String term) {
		return QueryBuilders.multiMatchQuery(term)
				.field("title", 2)
				.field("description", 1)
				.fuzziness(Fuzziness.TWO);
	}

	private String printHit(SearchHit<Book> hit) {
		Book book = hit.getContent();
		return book.title() + " (" + hit.getScore() + ")";
	}
}
