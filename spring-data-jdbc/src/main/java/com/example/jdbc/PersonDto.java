/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.jdbc;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@ToString
public class PersonDto {
	private final String name;
	private final String town;

	public static class PersonDtoRowMapper implements RowMapper<PersonDto> {
		@Override
		public PersonDto mapRow(ResultSet resultSet, int i) throws SQLException {
			String name = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
			String town = resultSet.getString("city");
			return new PersonDto(name, town);
		}
	}
}
