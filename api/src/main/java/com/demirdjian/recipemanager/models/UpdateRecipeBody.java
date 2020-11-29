package com.demirdjian.recipemanager.models;

import java.util.Arrays;

// TODO: Implement Class-Level constraints so that at minimum one field is not Empty
public class UpdateRecipeBody {

	private int id;
	private String title;
	private String[] ingredients;
	private String description;
	private String[] steps;

	public UpdateRecipeBody() {
		this.id = -1;
		this.title = "";
		this.ingredients = new String[0];
		this.description = "";
		this.steps = new String[0];
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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