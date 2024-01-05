package com.bugflix.weblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WeblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeblogApplication.class, args);
	}

}
