package com.demirdjian.recipemanager.converter;

import com.demirdjian.recipemanager.models.Category;

import org.springframework.core.convert.converter.Converter;

public class CategoryConverter implements Converter<String, Category> {

	/**
	 * Converts String to Category for optional search Parameter.
	 * 
	 * @param source
	 * @return Category
	 */
	@Override
	public Category convert(String source) {
		Category response = null;
		if (source != null) {
			try {
				response = Category.valueOf(source.toUpperCase());
			} catch (IllegalArgumentException e) {
				return Category.INVALID;
			}
		}
		return response;
	}

}
