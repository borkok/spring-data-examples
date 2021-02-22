package com.example.jdbcapi;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.List;

@Log
@SpringBootApplication
public class SpringJdbcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringJdbcApplication.class, args);
		PersonRepository personRepository = applicationContext.getBean(PersonRepository.class);

		Person person = new Person("Anna", "Wanna", LocalDate.parse("1987-09-26"));
		personRepository.save(person);

		List<Person> persons = List.of(
				new Person("Wanda", "Anda", LocalDate.parse("1977-09-06")),
				new Person("Mariola", "Nowak", LocalDate.parse("1997-08-16")),
				new Person("Patrycja", "Kowalska", LocalDate.parse("1994-01-23"))
		);
		personRepository.save(persons);

		log.info("Znaleziono: " + personRepository.findAll());
	}
}
