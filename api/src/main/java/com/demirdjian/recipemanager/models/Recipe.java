package com.demirdjian.recipemanager.models;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recipes")
public class Recipe {

	@Id
	private String id;

	@TextIndexed
	private String title;
	@TextIndexed
	private String description;
	@TextIndexed
	private Ingredient[] ingredients;

	private String[] steps;

	/**
	 * Empty Constructor.
	 */
	public Recipe() {
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param title
	 * @param ingredients
	 * @param description
	 * @param steps
	 */
	public Recipe(String id, String title, Ingredient[] ingredients, String description, String[] steps) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Custom toString for debug printing.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuilder returnStr = new StringBuilder();
		returnStr.append("Title:\t\t" + this.title + "\n");
		returnStr.append("Description:\t" + this.description + "\n");
		returnStr.append("Ingredients:\t" + Arrays.toString(this.ingredients) + "\n");
		returnStr.append("Steps:\t\t" + Arrays.toString(this.steps) + "\n");
		returnStr.append("ID:\t\t" + this.id + "\n");

		return returnStr.toString();
	}

}
