/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdataelastic;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, Long> {

	// {"bool":{"must":[{"query_string":{"query":"*Programming*","fields":["title^1.0"],"type":"best_fields","default_operator":"or","max_determinized_states":10000,"enable_position_increments":true,"fuzziness":"AUTO","fuzzy_prefix_length":0,"fuzzy_max_expansions":50,"phrase_slop":0,"analyze_wildcard":true,"escape":false,"auto_generate_synonyms_phrase_query":true,"fuzzy_transpositions":true,"boost":1.0}}],"adjust_pure_negative":true,"boost":1.0}},"version":true,"sort":[{"published":{"order":"desc"}}]}"
	List<SearchHit<Book>> findByTitleContainingOrderByPublishedDesc(String title);

	// {"bool":{"must":[{"range":{"published":{"from":"20171001","to":null,"include_lower":false,"include_upper":true,"boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}}
	List<SearchHit<Book>> findByPublishedGreaterThan(LocalDate localDate);
}
