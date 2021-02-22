/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdatamongodb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Accessors(fluent=true)
@Getter
@Setter
@ToString
class Library {
	@GeoSpatialIndexed(name = "library.location")
	private Point location;
	private String city;

	Library() {}

	Library(String name, double lat, double lon) {
		this.location = new Point(lat, lon);
		this.city = name;
	}
}
