package com.demirdjian.recipemanager.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.demirdjian.recipemanager.models.CookingMethod;

public class CookingMethodValidator implements ConstraintValidator<CookingMethodConstraint, CookingMethod> {

	@Override
	public void initialize(CookingMethodConstraint cookingMethod) {
		// initializes validator before running isValid
	}

	@Override
	public boolean isValid(CookingMethod cookingMethod, ConstraintValidatorContext cxt) {
		return cookingMethod != CookingMethod.INVALID;
	}

}
