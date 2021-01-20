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
	public boolean isValid(UpdateRecipeBody updateRecipe, ConstraintValidatorContext context) {
		// updateRecipe is invalid if all properties are null
		// or a provided property is Empty
		// Category and Cooking Method are validated through their custom constraints

		if (updateRecipe.getDescription() == null && updateRecipe.getIngredients() == null
				&& updateRecipe.getSteps() == null && updateRecipe.getTitle() == null
				&& updateRecipe.getAuthor() == null && updateRecipe.getServings() == null) {
			return false;
		}

		boolean response = true;
		context.disableDefaultConstraintViolation();

		if (updateRecipe.getDescription() != null && updateRecipe.getDescription().isBlank()) {
			context.buildConstraintViolationWithTemplate("Description may not be empty.").addConstraintViolation();
			response = false;
		}
		if (updateRecipe.getIngredients() != null && updateRecipe.getIngredients().length == 0) {
			context.buildConstraintViolationWithTemplate("Ingredients may not be empty.").addConstraintViolation();
			response = false;
		}
		if (updateRecipe.getSteps() != null && updateRecipe.getSteps().length == 0) {
			context.buildConstraintViolationWithTemplate("Steps may not be empty.").addConstraintViolation();
			response = false;
		}
		if (updateRecipe.getTitle() != null && updateRecipe.getTitle().isBlank()) {
			context.buildConstraintViolationWithTemplate("Title may not be empty.").addConstraintViolation();
			response = false;
		}
		if (updateRecipe.getAuthor() != null && updateRecipe.getAuthor().isBlank()) {
			context.buildConstraintViolationWithTemplate("Author may not be empty.").addConstraintViolation();
			response = false;
		}
		if (updateRecipe.getServings() != null && updateRecipe.getServings() < 1) {
			context.buildConstraintViolationWithTemplate("Servings may not be empty or less than 1.")
					.addConstraintViolation();
			response = false;
		}

		return response;

	}

}
