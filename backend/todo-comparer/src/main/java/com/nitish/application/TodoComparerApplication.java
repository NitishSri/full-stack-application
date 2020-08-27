package com.nitish.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoComparerApplication {

	@Value("${spring.data.mongodb.host}")
	String hostname;

	@Value("${spring.data.mongodb.port}")
	int port;

	@Value("${spring.data.mongodb.database}")
	String databaseName;

	public static void main(String[] args) {
		SpringApplication.run(TodoComparerApplication.class, args);
	}

}
