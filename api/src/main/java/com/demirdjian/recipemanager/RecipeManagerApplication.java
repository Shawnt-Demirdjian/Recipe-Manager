package com.demirdjian.recipemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RecipeManagerApplication {

	/**
	 * Run the Spring Boot Application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(RecipeManagerApplication.class, args);
	}

	/**
	 * Configures the CORS allowed Origins properties.
	 * 
	 * @return WebMvcConfigurer
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

}
