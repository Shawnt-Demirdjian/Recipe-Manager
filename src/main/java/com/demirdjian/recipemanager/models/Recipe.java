package com.demirdjian.recipemanager.models;

import java.util.Arrays;

public class Recipe {
	private int id;
	private String title;
	private String[] ingredients;
	private String description;
	private String[] steps;

	public Recipe() {
		this.id = -1;
		this.title = "";
		this.ingredients = new String[0];
		this.description = "";
		this.steps = new String[0];
	}

	public Recipe(int id, String title, String[] ingredients, String description, String[] steps) {
		this.id = id;
		this.title = title;
		this.ingredients = ingredients;
		this.description = description;
		this.steps = steps;
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
		return this.title + "\n" + this.description + "\n" + Arrays.toString(this.ingredients) + "\n"
				+ Arrays.toString(this.steps) + "\n" + this.id;
	}

}