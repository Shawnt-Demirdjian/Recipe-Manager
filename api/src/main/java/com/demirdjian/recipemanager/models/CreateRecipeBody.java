package com.demirdjian.recipemanager.models;

import java.util.Arrays;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.demirdjian.recipemanager.validator.CategoryConstraint;
import com.demirdjian.recipemanager.validator.CookingMethodConstraint;

public class CreateRecipeBody {
	@NotBlank(message = "Title may not be empty.")
	private String title;

	@NotEmpty(message = "Ingredients may not be empty.")
	private Ingredient[] ingredients;

	@NotBlank(message = "Description may not be empty.")
	private String description;

	@NotEmpty(message = "Steps may not be empty.")
	private String[] steps;

	@NotNull(message = "Category may not be empty.")
	@CategoryConstraint
	private Category category;

	@NotNull(message = "Cooking Method may not be empty.")
	@CookingMethodConstraint
	private CookingMethod cookingMethod;

	@NotBlank(message = "Author may not be empty.")
	private String author;

	@NotNull(message = "Servings may not be empty.")
	@Min(value = 1, message = "Servings may not be less than 1.")
	private Integer servings;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Ingredient[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(Ingredient[] ingredients) {
		this.ingredients = ingredients;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getSteps() {
		return steps;
	}

	public void setSteps(String[] steps) {
		this.steps = steps;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CookingMethod getCookingMethod() {
		return cookingMethod;
	}

	public void setCookingMethod(CookingMethod cookingMethod) {
		this.cookingMethod = cookingMethod;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	/**
	 * Custom toString for debug printing.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuilder returnStr = new StringBuilder();
		returnStr.append("Title:\t\t" + this.title + "\n");
		returnStr.append("Author:\t\t" + this.author + "\n");
		returnStr.append("Description:\t" + this.description + "\n");
		returnStr.append("Ingredients:\t" + Arrays.toString(this.ingredients) + "\n");
		returnStr.append("Steps:\t\t" + Arrays.toString(this.steps) + "\n");
		returnStr.append("Category:\t" + this.category + "\n");
		returnStr.append("Cooking Method:\t" + this.cookingMethod + "\n");
		returnStr.append("Servings:\t" + this.servings + "\n");

		return returnStr.toString();
	}
}
