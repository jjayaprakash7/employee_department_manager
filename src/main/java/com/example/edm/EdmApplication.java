package com.example.edm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdmApplication.class, args);
	}

}
