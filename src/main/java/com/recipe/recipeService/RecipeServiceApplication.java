package com.recipe.recipeService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.recipe.recipeService.service.RecipeService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
public class RecipeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeServiceApplication.class, args);
	}
	
	 @Bean
	 public CommandLineRunner run(RecipeService recipeService) {
	        return args -> {
	            recipeService.loadRecipesFromAPI();
	        };
	    }

}
