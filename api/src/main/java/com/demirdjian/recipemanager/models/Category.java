package com.demirdjian.recipemanager.models;

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
}
