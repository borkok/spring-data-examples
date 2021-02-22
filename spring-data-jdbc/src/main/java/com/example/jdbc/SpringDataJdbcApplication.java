package com.example.jdbc;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@Log
@SpringBootApplication
public class SpringDataJdbcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringDataJdbcApplication.class, args);
		PersonRepository personRepository = applicationContext.getBean(PersonRepository.class);
		Person person = Person.of("Anna", "Wanna", LocalDate.parse("1987-09-26"),
				new Address("Rybacka", "Hel"),
				new Nickname("Halinka"),
				new Nickname("Marzenka"));
		personRepository.save(person);

		personRepository.saveAll(List.of(
				Person.of("Maria", "Zawada", LocalDate.parse("1984-09-26"),
						new Address("Rybacka", "Hell"),
						new Nickname("Maryla")),
				Person.of("Lidia", "Antena", LocalDate.parse("1983-09-26"),
						new Address("Rybacka", "Helowo"),
						new Nickname("Lidka"))
		));

		log.info("Znaleziono: " + personRepository.findById(1L));
		log.info("Znaleziono po imieniu: " + personRepository.findByFirstnameContaining("Mar"));
		log.info("Znaleziono by city: " + personRepository.findByAddressCity("Hel"));
		List<PersonDto> dtoById = personRepository.findDtoById(1L);
		log.info("Znaleziono dto: " + dtoById);

		log.info("Znaleziono page 1 of 2: " + personRepository.findAll(PageRequest.of(0, 2, Sort.by("lastname"))).toList());
	}

}
