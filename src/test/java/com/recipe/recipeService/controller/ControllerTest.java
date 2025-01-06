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
    void testHello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    void testSaveRecipes() throws Exception {
        // Prepare mock data
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Pasta");
        recipes.add(recipe);

        // Mock service behavior
        when(recipeService.saveRecipes(recipes)).thenReturn(recipes);

        mockMvc.perform(post("/recipes/list")
                        .contentType("application/json")
                        .content("[{\"name\":\"Pasta\",\"prepTimeMinutes\":10}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Pasta")));

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
        when(recipeService.getRecipeById(1)).thenThrow(new ResourceNotFoundException("Recipe with ID 1 not found"));

        mockMvc.perform(get("/recipes/list/1000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Resource Not Found")));
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
                        .param("searchParan", "Pasta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Pasta")));

        // Verify interaction with the service
        verify(recipeService, times(1)).searchRecipes("Pasta");
    }
}
