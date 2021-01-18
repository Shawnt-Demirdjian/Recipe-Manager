package com.demirdjian.recipemanager.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CookingMethod {
	STOVE, OVEN, BBQ, FRYER, NONE;

	/**
	 * Convert String to CookingMethod.
	 * 
	 * @param value
	 * @return CookingMethod
	 */
	@JsonCreator
	public static CookingMethod getCookingMethodFromString(String value) {
		CookingMethod response = null;
		try {
			response = CookingMethod.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
		return response;
	}
}
