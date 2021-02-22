/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdataelastic;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.DistanceFeatureQueryBuilder;
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
public class GeoSpatialQueriesService {
	//ES repository does not support geo queries
	//so I need to go with operations
	private final ElasticsearchOperations elasticsearchOperations;

	public GeoSpatialQueriesService(ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	//{"distance_feature":{"field":"library.location","origin":{"lat":54.50175034527047,"lon":18.538316714853877},"pivot":"600km","boost":1.0}}
	public List<String> findBooksNearGdynia() {
		GeoPoint gdyniaPoint = new GeoPoint(54.50175034527047, 18.538316714853877);
		DistanceFeatureQueryBuilder.Origin origin = new DistanceFeatureQueryBuilder.Origin(gdyniaPoint);
		String pivot = "600km"; // Distance from the origin at which relevance scores receive half of the boost value.

		// Boosts the relevance score of documents closer to a provided origin date or point.
		DistanceFeatureQueryBuilder queryBuilder = QueryBuilders.distanceFeatureQuery("library.location", origin, pivot);
		Query query = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder)
				.build();
		SearchHits<Book> searchHits = elasticsearchOperations.search(query, Book.class);
		return searchHits.getSearchHits().stream()
				.map(this::printHit)
				.collect(Collectors.toList());
	}

	private String printHit(SearchHit<Book> hit) {
		Book book = hit.getContent();
		return "Score: " + hit.getScore() + "| Book city: " + book.library().city();
	}
}
