package com.todo.user;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
public class TodoLoginApplication implements CommandLineRunner {

	@Autowired
	DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(TodoLoginApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
	}

}
