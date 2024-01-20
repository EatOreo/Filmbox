package com.eatoreo.filmbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring6.SpringTemplateEngine;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@SpringBootApplication
public class FilmboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmboxApplication.class, args);
	}

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}
