package com.demirdjian.recipemanager.converter;

import com.demirdjian.recipemanager.models.CookingMethod;

import org.springframework.core.convert.converter.Converter;

public class CookingMethodConverter implements Converter<String, CookingMethod> {

	/**
	 * Converts String to Cooking Method for optional search Parameter.
	 * 
	 * @param source
	 * @return CookingMethod
	 */
	@Override
	public CookingMethod convert(String source) {
		CookingMethod response = null;
		if (source != null) {
			try {
				response = CookingMethod.valueOf(source.toUpperCase());
			} catch (IllegalArgumentException e) {
				return CookingMethod.INVALID;
			}
		}
		return response;
	}

}
