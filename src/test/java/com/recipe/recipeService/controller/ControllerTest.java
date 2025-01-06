package com.recipe.recipeService.controller;

import com.recipe.recipeService.exception.ResourceNotFoundException;
import com.recipe.recipeService.model.Recipe;
import com.recipe.recipeService.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ControllerTest {

    @InjectMocks
    private Controller controller;

    @Mock
    private RecipeService recipeService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Ensure Mockito initializes the mocks
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testSaveRecipes() throws Exception {
        // Prepare mock data
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(2001);
        recipe.setName("Classic Margherita Pizza");
        recipes.add(recipe);

        // Mock service behavior
        when(recipeService.saveRecipes(recipes)).thenReturn(recipes);

        mockMvc.perform(post("/recipes/list")
                        .contentType("application/json")
                        .content("[{ \"id\": 2001,\"name\":\"Classic Margherita Pizza\",\"ingredients\":[\"Pizza dough\",\"Tomato sauce\",\"Fresh mozzarella cheese\",\"Fresh basil leaves\",\"Olive oil\",\"Salt and pepper to taste\"],\"instructions\":[\"Preheat the oven to 475°F (245°C).\",\"Roll out the pizza dough and spread tomato sauce evenly.\",\"Top with slices of fresh mozzarella and fresh basil leaves.\",\"Drizzle with olive oil and season with salt and pepper.\",\"Bake in the preheated oven for 12-15 minutes or until the crust is golden brown.\",\"Slice and serve hot.\"],\"prepTimeMinutes\":20,\"cookTimeMinutes\":15,\"servings\":4,\"difficulty\":\"Easy\",\"cuisine\":\"Italian\",\"caloriesPerServing\":300,\"tags\":[\"Pizza\",\"Italian\"],\"userId\":166,\"image\":\"https://cdn.dummyjson.com/recipe-images/1.webp\",\"rating\":4.6,\"reviewCount\":98,\"mealType\":[\"Dinner\"]}]"
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Classic Margherita Pizza")));

        // Verify interaction with the service
        verify(recipeService, times(1)).saveRecipes(recipes);
    }

    @Test
    void testGetAllRecipes() throws Exception {
        // Prepare mock data
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Pasta");
        recipes.add(recipe);

        // Mock service behavior
        when(recipeService.getAllRecipes()).thenReturn(recipes);

        mockMvc.perform(get("/recipes/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Pasta")));

        // Verify interaction with the service
        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    void testGetRecipeByIdSuccess() throws Exception {
        // Prepare mock data
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Pasta");

        // Mock service behavior
        when(recipeService.getRecipeById(1)).thenReturn(recipe);

        mockMvc.perform(get("/recipes/list/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pasta")));

        // Verify interaction with the service
        verify(recipeService, times(1)).getRecipeById(1);
    }

    @Test
    void testGetRecipeByIdNotFound() throws Exception {
        // Mock service behavior to throw exception
        when(recipeService.getRecipeById(1000)).thenThrow(new ResourceNotFoundException("Recipe with ID 1000 not found"));

        mockMvc.perform(get("/recipes/list/1000"))
                .andExpect(status().isNotFound()) // Ensure the status is 404
                .andExpect(jsonPath("$.error", is("Resource Not Found"))) // Check the error key
                .andExpect(jsonPath("$.message", is("Recipe with ID 1000 not found"))); // Check the detailed error message
    }

    @Test
    void testSearchRecipes() throws Exception {
        // Prepare mock data
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Pasta");
        recipes.add(recipe);

        // Mock service behavior
        when(recipeService.searchRecipes("Pasta")).thenReturn(recipes);

        mockMvc.perform(get("/recipes/search")
                        .param("searchParam", "Pasta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Pasta")));

        // Verify interaction with the service
        verify(recipeService, times(1)).searchRecipes("Pasta");
    }
}
