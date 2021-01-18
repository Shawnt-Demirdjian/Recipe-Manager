package com.demirdjian.recipemanager.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.demirdjian.recipemanager.models.Category;

public class CategoryValidator implements ConstraintValidator<CategoryConstraint, Category> {

	@Override
	public void initialize(CategoryConstraint category) {
		// initializes validator before running isValid
	}

	@Override
	public boolean isValid(Category category, ConstraintValidatorContext cxt) {
		return category != Category.INVALID;
	}

}
