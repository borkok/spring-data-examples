package com.example.jdbcapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PersonRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public List<Person> findAll() {
		return jdbcTemplate.query("select * from PERSON", new BeanPropertyRowMapper<>(Person.class));
	}

	public Person findById(Long id) {
		return jdbcTemplate.queryForObject("select * from PERSON where id=:id", Map.of("id", id), new BeanPropertyRowMapper<>(Person.class));
	}

	public void save(Person person) {
		jdbcTemplate.update("insert into PERSON (firstname, lastname, birthdate) values (:firstname, :lastname, :birthdate)",
				new BeanPropertySqlParameterSource(person));
	}

	public void save(List<Person> persons) {
		jdbcTemplate.batchUpdate("insert into PERSON (firstname, lastname, birthdate) values (:firstname, :lastname, :birthdate)",
				SqlParameterSourceUtils.createBatch(persons.toArray()));
	}
}
