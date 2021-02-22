/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDate;
import java.util.Set;

//encja
@AllArgsConstructor
@ToString
public class Person {
	@Id
	private final Long id;
	@Version
	private final Integer version;
	private final String firstname;
	private final String lastname;
	private final LocalDate birthdate;

	private final Address address;
	private final Set<Nickname> nicknames;

	public static Person of(String first, String last, LocalDate birthday, Address address, Nickname... nicks) {
		return new Person(null, null, first, last, birthday, address, Set.of(nicks));
	}
}
