package com.recipe.recipeService.repository;

import com.recipe.recipeService.model.Recipe;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}

