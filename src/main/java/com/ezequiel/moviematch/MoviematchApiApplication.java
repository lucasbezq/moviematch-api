package com.ezequiel.moviematch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MoviematchApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviematchApiApplication.class, args);
	}

}
