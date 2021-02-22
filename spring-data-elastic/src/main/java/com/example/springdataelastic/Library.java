/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdataelastic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Accessors(fluent=true)
@Getter
@Setter
@ToString
class Library {
	private GeoPoint location;
	private String city;

	Library() {}

	Library(String name, double lat, double lon) {
		this.location = new GeoPoint(lat, lon);
		this.city = name;
	}
}