package com.example.springdatamongodb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DataCassandraTest
class SpringDataMongodbApplicationTests {

	@Test
	void contextLoads() {
	}

}
