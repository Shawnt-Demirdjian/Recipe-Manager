package com.demirdjian.recipemanager.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.demirdjian.recipemanager.models.UpdateRecipeBody;

public class UpdateRecipeValidator implements ConstraintValidator<UpdateRecipeConstraint, UpdateRecipeBody> {

	@Override
	public void initialize(UpdateRecipeConstraint updateRecipe) {
		// initializes validator before running isValid
	}

	@Override
	public boolean isValid(UpdateRecipeBody updateRecipe, ConstraintValidatorContext cxt) {
		// updateRecipe is Invalid if all properties are null
		return (updateRecipe.getCategory() != null || updateRecipe.getCookingMethod() != null
				|| updateRecipe.getDescription() != null || updateRecipe.getIngredients() != null
				|| updateRecipe.getSteps() != null || updateRecipe.getTitle() != null);
	}

}
