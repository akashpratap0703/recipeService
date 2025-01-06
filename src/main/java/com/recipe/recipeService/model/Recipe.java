package com.recipe.recipeService.model;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
@Indexed
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Name cannot be empty") // Validate that name is not blank
	@Column(name = "name", nullable = false)
	@FullTextField
	private String name;

	@Min(value = 1, message = "Preparation time must be greater than 0 minutes") // Validate that prep time is greater																				// than 0
	@Column(name = "prepTimeMinutes")
	private int prepTimeMinutes;

	@Min(value = 1, message = "Cook time must be greater than 0 minutes") // Validate that cook time is greater than 0
	@Column(name = "cookTimeMinutes")
	private int cookTimeMinutes;

	@Min(value = 1, message = "Servings must be greater than 0") // Validate that servings are greater than 0
	@Column(name = "servings")
	private int servings;

	@Size(min = 3, max = 20, message = "Difficulty should be between 3 and 20 characters") // Validate difficulty length
	@Column(name = "difficulty")
	private String difficulty;

	@NotBlank(message = "Cuisine cannot be empty") // Validate that cuisine is not blank
	@Column(name = "cuisine")
	@FullTextField
	private String cuisine;

	@Min(value = 0, message = "Calories per serving must be a positive number") // Validate that calories are positive
	@Column(name = "caloriesPerServing")
	private int caloriesPerServing;

	@Column(name = "image")
	private String image;

	@Min(value = 0, message = "Rating must be a positive number") // Validate that rating is positive
	@Column(name = "rating", precision = 3)
	private double rating;

	@Min(value = 0, message = "Review count must be a positive number") // Validate that review count is positive
	@Column(name = "reviewCount")
	private int reviewCount;

	@Column(name = "userId")
	private int userId;

	@JsonIgnore
	@Column(name = "ingredients")
	private String dbIngredients;

	@JsonIgnore
	@Column(name = "Instructions")
	private String dbInstructions;

	@JsonIgnore
	@Column(name = "tags")
	private String dbTags;

	@JsonIgnore
	@Column(name = "meal_types")
	private String dbMealType;

	@Transient
	private List<String> ingredients;
	@Transient
	private List<String> instructions;
	@Transient
	private List<String> tags;
	@Transient
	private List<String> mealType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrepTimeMinutes() {
		return prepTimeMinutes;
	}

	public void setPrepTimeMinutes(int prepTimeMinutes) {
		this.prepTimeMinutes = prepTimeMinutes;
	}

	public int getCookTimeMinutes() {
		return cookTimeMinutes;
	}

	public void setCookTimeMinutes(int cookTimeMinutes) {
		this.cookTimeMinutes = cookTimeMinutes;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public int getCaloriesPerServing() {
		return caloriesPerServing;
	}

	public void setCaloriesPerServing(int caloriesPerServing) {
		this.caloriesPerServing = caloriesPerServing;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDbIngredients() {
		return dbIngredients;
	}

	public void setDbIngredients(String dbIngredients) {
		this.dbIngredients = dbIngredients;
	}

	public String getDbInstructions() {
		return dbInstructions;
	}

	public void setDbInstructions(String dbInstructions) {
		this.dbInstructions = dbInstructions;
	}

	public String getDbTags() {
		return dbTags;
	}

	public void setDbTags(String dbTags) {
		this.dbTags = dbTags;
	}

	public String getDbMealType() {
		return dbMealType;
	}

	public void setDbMealType(String dbMealType) {
		this.dbMealType = dbMealType;
	}

	public List<String> getIngredients() {
		if (ingredients == null && dbIngredients != null) {
			ingredients = Arrays.asList(dbIngredients.split("#"));
		}
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
		this.dbIngredients = String.join("#", ingredients);
	}

	public List<String> getInstructions() {
		if (instructions == null && dbInstructions != null) {
			instructions = Arrays.asList(dbInstructions.split("#"));
		}
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
		this.dbInstructions = String.join("#", instructions);
	}

	public List<String> getTags() {
		if (tags == null && dbTags != null) {
			tags = Arrays.asList(dbTags.split("#"));
		}
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
		this.dbTags = String.join("#", tags);
	}

	public List<String> getMealType() {
		if (mealType == null && dbMealType != null) {
			mealType = Arrays.asList(dbMealType.split("#"));
		}
		return mealType;
	}

	public void setMealType(List<String> mealType) {
		this.mealType = mealType;
		this.dbMealType = String.join("#", mealType);
	}

}
