package com.todo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
public class TodoLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoLoginApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

}
