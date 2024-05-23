package com.ssafy.starflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StarflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarflixApplication.class, args);
	}

}
