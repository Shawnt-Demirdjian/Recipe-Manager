package com.demirdjian.recipemanager.models;

import java.util.Arrays;

import com.demirdjian.recipemanager.validator.CategoryConstraint;
import com.demirdjian.recipemanager.validator.CookingMethodConstraint;
import com.demirdjian.recipemanager.validator.UpdateRecipeConstraint;

@UpdateRecipeConstraint
public class UpdateRecipeBody {

	private int id;
	private String title;
	private Ingredient[] ingredients;
	private String description;
	private String[] steps;
	private String author;
	private Integer servings;

	@CategoryConstraint
	private Category category;
	@CookingMethodConstraint
	private CookingMethod cookingMethod;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
