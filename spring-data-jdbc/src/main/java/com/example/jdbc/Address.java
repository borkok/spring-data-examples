/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.jdbc;

import lombok.AllArgsConstructor;
import lombok.ToString;

//entity
@AllArgsConstructor
@ToString
public class Address {
	private final String street;
	private final String city;
}
