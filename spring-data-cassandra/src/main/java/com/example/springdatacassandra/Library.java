package com.example.springdatacassandra;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Element;
import org.springframework.data.cassandra.core.mapping.Tuple;
import org.springframework.data.geo.Point;

@Tuple
@Accessors(fluent=true)
@Getter
@Setter
@ToString
class Library {
	@Element(0)
	private double lat;
	@Element(1)
	private double lon;
	@Element(2)
	private String city;

	Library() {}

	Library(String name, double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
		this.city = name;
	}
}
