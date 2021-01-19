package com.demirdjian.recipemanager.controller;

import com.demirdjian.recipemanager.models.Category;
import com.demirdjian.recipemanager.models.CookingMethod;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api")
public class OptionsController {

	/**
	 * Returns list of available Categories.
	 *
	 * @return Category[]
	 */
	@GetMapping("/categories")
	public Category[] getCategories() {
		return Category.validValues();
	}

	/**
	 * Returns list of available Cooking Methods.
	 *
	 * @return CookingMethod[]
	 */
	@GetMapping("/cooking-methods")
	public CookingMethod[] getCookingMethods() {
		return CookingMethod.validValues();
	}
}
