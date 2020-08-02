package com.recipe.ws;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.slugify.Slugify;
import com.recipe.ws.security.AppProperties;


@SpringBootApplication
public class RecipeWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeWsApplication.class, args);
	}

	@Bean 
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	Slugify slugify() {
		return new Slugify();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean(name="AppProperties")
	public AppProperties getAppProperties() {
		return new AppProperties();
	}
	
}
