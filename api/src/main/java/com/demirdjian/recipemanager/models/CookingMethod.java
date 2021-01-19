package com.demirdjian.recipemanager.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CookingMethod {
	STOVE, OVEN, BBQ, FRYER, NONE, INVALID;

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
			return CookingMethod.INVALID;
		}
		return response;
	}

	/**
	 * Returns array of VALID Cooking Methods.
	 * 
	 * @return CookingMethod[]
	 * 
	 */
	public static CookingMethod[] validValues() {
		// Remove INVALID from CookingMethod.values()
		List<CookingMethod> response = new ArrayList<>();
		Collections.addAll(response, CookingMethod.values());
		response.removeIf(x -> (x == CookingMethod.INVALID));
		return response.toArray(new CookingMethod[0]);
	}
}
