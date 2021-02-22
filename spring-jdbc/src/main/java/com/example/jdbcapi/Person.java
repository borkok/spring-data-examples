/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.jdbcapi;

import lombok.*;

import java.time.LocalDate;

//encja
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Person {
	private Long id;
	private String firstname;
	private String lastname;
	private LocalDate birthdate;

	public Person(String firstname, String lastname, LocalDate birthdate) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
	}
}
