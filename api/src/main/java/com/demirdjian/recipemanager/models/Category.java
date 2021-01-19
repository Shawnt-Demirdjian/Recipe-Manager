package com.demirdjian.recipemanager.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
	SIDE, ENTREE, DESSERT, APPETIZER, INVALID;

	/**
	 * Convert String to Category.
	 * 
	 * @param value
	 * @return Category
	 */
	@JsonCreator
	public static Category getCategoryFromString(String value) {
		Category response = null;
		try {
			response = Category.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			return Category.INVALID;
		}
		return response;
	}

	/**
	 * Returns array of VALID Categories.
	 * 
	 * @return Category[]
	 * 
	 */
	public static Category[] validValues() {
		// Remove INVALID from Category.values()
		List<Category> response = new ArrayList<>();
		Collections.addAll(response, Category.values());
		response.removeIf(x -> (x == Category.INVALID));
		return response.toArray(new Category[0]);
	}
}
