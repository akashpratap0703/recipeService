package com.recipe.recipeService.service;

import com.recipe.recipeService.model.Recipe;
import com.recipe.recipeService.model.RecipeApiResponse;
import com.recipe.recipeService.repository.RecipeRepository;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.hibernate.search.engine.search.query.SearchQuery;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private EntityManager entityManager;

    private final RestTemplate restTemplate;

    public RecipeService(RecipeRepository recipeRepository, RestTemplate restTemplate) {
        this.recipeRepository = recipeRepository;
        this.restTemplate = restTemplate;
    }

    public void loadRecipesFromAPI() {
        String url = "https://dummyjson.com/recipes";
        logger.info("Fetching recipes from API: {}", url);

        // Fetch data from the API
        RecipeApiResponse apiResponse = restTemplate.getForObject(url, RecipeApiResponse.class);

        // Save the recipes into the H2 database
        if (apiResponse != null && apiResponse.getRecipes() != null) {
            logger.info("Fetched {} recipes from API, saving them to the database", apiResponse.getRecipes().size());
            recipeRepository.saveAll(apiResponse.getRecipes());
            logger.info("Successfully saved {} recipes to the database", apiResponse.getRecipes().size());
        } else {
            logger.warn("No recipes fetched or API response is empty");
        }
    }

    public List<Recipe> searchRecipes(String nameQuery) {
        logger.info("Searching for recipes with query: {}", nameQuery);

        try {
            logger.debug("Starting Hibernate Search indexing...");
            Search.session(entityManager).massIndexer().startAndWait();
            logger.debug("Hibernate Search indexing completed");
        } catch (InterruptedException e) {
            logger.error("Error during Hibernate Search indexing", e);
            Thread.currentThread().interrupt();
        }

        SearchSession searchSession = Search.session(entityManager);

        SearchQuery<Recipe> query = searchSession.search(Recipe.class)
                .where(f -> f.bool()
                        .should(f.wildcard().field("name").matching("*" + nameQuery + "*"))    // Match on 'name' field
                        .should(f.wildcard().field("cuisine").matching("*" + nameQuery + "*"))  // Match on 'cuisine' field
                )
                .toQuery();

        List<Recipe> recipes = query.fetchAllHits();
        logger.info("Found {} recipes matching the query", recipes.size());

        return recipes;
    }

    public List<Recipe> saveRecipes(List<Recipe> recipes) {
        logger.info("Saving {} recipes to the database", recipes.size());
        List<Recipe> savedRecipes = recipeRepository.saveAll(recipes);
        logger.info("Successfully saved {} recipes", savedRecipes.size());
        return savedRecipes;
    }

    public List<Recipe> getAllRecipes() {
        logger.info("Fetching all recipes from the database");
        List<Recipe> recipes = recipeRepository.findAll();
        logger.info("Fetched {} recipes from the database", recipes.size());
        return recipes;
    }

    public Recipe getRecipeById(Integer id) {
        logger.info("Fetching recipe with ID: {}", id);
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> {
            logger.error("Recipe with ID {} not found", id);
            return new RuntimeException("Recipe not found");
        });
        logger.info("Successfully fetched recipe with ID: {}", id);
        return recipe;
    }

    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
}
