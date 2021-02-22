/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.jdbc;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
	public List<Person> findByFirstnameContaining(String name);

	@Query(value="select firstname, lastname, a.city from Person p join Address a on p.id = a.person where p.id = :id",
	rowMapperClass=PersonDto.PersonDtoRowMapper.class)
	public List<PersonDto> findDtoById(@Param("id") Long id);

	@Query("select * from Person p join Address a on p.id = a.person where a.city = :city")
	public List<Person> findByAddressCity(@Param("city") String city);
}
