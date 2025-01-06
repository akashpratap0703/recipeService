package com.recipe.recipeService.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Recipe API", version = "v1", description = "API for managing recipes"))
public class OpenAPIConfig {
}
