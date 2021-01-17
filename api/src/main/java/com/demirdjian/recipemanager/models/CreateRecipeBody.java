package com.demirdjian.recipemanager.models;

import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CreateRecipeBody {
	@NotBlank(message = "Title may not be empty.")
	private String title;

	@NotEmpty(message = "Ingredients may not be empty.")
	private Ingredient[] ingredients;

	@NotBlank(message = "Description may not be empty.")
	private String description;

	@NotEmpty(message = "Steps may not be empty.")
	private String[] steps;

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

	public String toString() {
		StringBuilder returnStr = new StringBuilder();
		returnStr.append("Title:\t\t" + this.title + "\n");
		returnStr.append("Description:\t" + this.description + "\n");
		returnStr.append("Ingredients:\t" + Arrays.toString(this.ingredients) + "\n");
		returnStr.append("Steps:\t\t" + Arrays.toString(this.steps) + "\n");

		return returnStr.toString();
	}

}