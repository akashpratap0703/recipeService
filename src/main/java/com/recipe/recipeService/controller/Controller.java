package com.recipe.recipeService.controller;

import com.recipe.recipeService.exception.ResourceNotFoundException;
import com.recipe.recipeService.model.Recipe;
import com.recipe.recipeService.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import javax.validation.Valid;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private RecipeService recipeService;

    @Operation(summary = "Get all recipes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched all recipes"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/recipes/list")
    public ResponseEntity<List<Recipe>> saveRecipes(@RequestBody @Valid List<Recipe> recipes) {
        logger.info("Request received to save recipes");
        List<Recipe> savedRecipes = recipeService.saveRecipes(recipes);
        logger.info("Recipes saved successfully: {}", savedRecipes.size());
        return ResponseEntity.ok(savedRecipes);
    }

    @Operation(summary = "Save a list of recipes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recipes saved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @GetMapping("/recipes/list")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        logger.info("Request received to fetch all recipes");
        List<Recipe> recipes = recipeService.getAllRecipes();
        logger.info("Number of recipes fetched: {}", recipes.size());
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "Get a recipe by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched recipe by ID"),
        @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @GetMapping("/recipes/list/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer id) {
        logger.info("Request received for recipe with ID: {}", id);
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            logger.error("Recipe with ID {} not found", id);
            throw new ResourceNotFoundException("Recipe with ID " + id + " not found");
        }
        logger.info("Recipe with ID {} fetched successfully", id);
        return ResponseEntity.ok(recipe);
    }
    
    @Operation(summary = "Search recipes based on a search parameter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched recipes based on search parameter"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameter")
    })
    @GetMapping("/recipes/search")
    public List<Recipe> search(@RequestParam String searchParam) {
        logger.info("Search request received with parameter: {}", searchParam);
        return recipeService.searchRecipes(searchParam);
    }

    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
}
