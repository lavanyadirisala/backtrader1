package com.spring.backtracking1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableScheduling
@EnableAutoConfiguration
public class Backtracking1Application {

	public static void main(String[] args) {
		SpringApplication.run(Backtracking1Application.class, args);
	}

}
