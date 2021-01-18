package com.demirdjian.recipemanager.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.demirdjian.recipemanager.db.RecipeRepository;
import com.demirdjian.recipemanager.models.CreateRecipeBody;
import com.demirdjian.recipemanager.models.Recipe;
import com.demirdjian.recipemanager.models.UpdateRecipeBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api")
public class WebController {

	@Autowired
	private RecipeRepository recipeRepository;
	private static final Logger RM_LOGGER = LoggerFactory.getLogger(WebController.class);

	/**
	 * Returns the recipe associated with the provided ID.
	 *
	 * @param id
	 * @param httpResponse
	 * @return Recipe or null if no recipes are found.
	 */
	@GetMapping("/recipes/{id}")
	public Recipe getRecipe(@PathVariable("id") @NotBlank String id, HttpServletResponse httpResponse) {
		Optional<Recipe> response = recipeRepository.findById(id);

		if (response.isPresent()) {
			RM_LOGGER.debug("Recipe Found: \n{}", response.get());
			return response.get();
		} else {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			RM_LOGGER.debug("No Recipe Found.");
			return null;
		}
	}

	/**
	 * Returns the recipes that have the query string in their title or description.
	 *
	 * @param queryString
	 * @param httpResponse
	 * @return List<Recipe> or null if no recipes are found.
	 */
	@GetMapping("/recipes")
	public List<Recipe> getRecipes(@RequestParam("queryString") @NotBlank String queryString,
			HttpServletResponse httpResponse) {
		List<Recipe> response = recipeRepository.searchByTitleAndDescription(queryString);
		if (response.isEmpty()) {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			RM_LOGGER.debug("No Recipes Found.");
		} else {
			RM_LOGGER.debug("Recipes Found: \n{}", response);
		}
		return response;
	}

	/**
	 * Creates a new recipe with the provided values.
	 *
	 * @param newRecipe
	 * @param httpResponse
	 * @return Recipe
	 */
	@PostMapping("/recipes")
	public Recipe createRecipe(@Valid @RequestBody CreateRecipeBody newRecipe, HttpServletResponse httpResponse) {
		Recipe recipe = new Recipe();
		recipe.setDescription(newRecipe.getDescription());
		recipe.setTitle(newRecipe.getTitle());
		recipe.setIngredients(newRecipe.getIngredients());
		recipe.setSteps(newRecipe.getSteps());
		recipe.setCategory(newRecipe.getCategory());
		recipe.setCookingMethod(newRecipe.getCookingMethod());
		recipeRepository.save(recipe);
		RM_LOGGER.debug("Recipe Created: \n{}", recipe);
		return recipe;
	}

	/**
	 * Updates the recipe of the provided ID with the provided values.
	 *
	 * @param id
	 * @param newRecipeParts
	 * @param httpResponse
	 * @return Recipe
	 */
	@PatchMapping("/recipes/{id}")
	public Recipe updateRecipe(@PathVariable("id") @NotBlank String id,
			@Valid @RequestBody UpdateRecipeBody newRecipeParts, HttpServletResponse httpResponse) {
		// Check if recipe with id exists
		Optional<Recipe> existingRecipe = recipeRepository.findById(id);
		if (!existingRecipe.isPresent()) {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			RM_LOGGER.debug("No Recipe Found.");
			return null;
		}

		// Update newRecipe with included parts from newRecipeParts
		Recipe newRecipe = existingRecipe.get();
		if (newRecipeParts.getTitle() != null && !newRecipeParts.getTitle().isEmpty()) {
			newRecipe.setTitle(newRecipeParts.getTitle());
		}
		if (newRecipeParts.getIngredients() != null && newRecipeParts.getIngredients().length != 0) {
			newRecipe.setIngredients(newRecipeParts.getIngredients());
		}
		if (newRecipeParts.getDescription() != null && !newRecipeParts.getDescription().isEmpty()) {
			newRecipe.setDescription(newRecipeParts.getDescription());
		}
		if (newRecipeParts.getSteps() != null && newRecipeParts.getSteps().length != 0) {
			newRecipe.setSteps(newRecipeParts.getSteps());
		}
		if (newRecipeParts.getCategory() != null) {
			newRecipe.setCategory(newRecipeParts.getCategory());
		}
		if (newRecipeParts.getCookingMethod() != null) {
			newRecipe.setCookingMethod(newRecipeParts.getCookingMethod());
		}

		// Update the recipe
		recipeRepository.save(newRecipe);
		RM_LOGGER.debug("Recipe Updated: \n{}", newRecipe);
		return newRecipe;
	}

	/**
	 * Deletes the recipe associated with the provided ID.
	 *
	 * @param id
	 * @param httpResponse
	 * @return Recipe
	 */
	@DeleteMapping("/recipes/{id}")
	public Recipe deleteRecipe(@PathVariable("id") @NotBlank String id, HttpServletResponse httpResponse) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		if (recipe.isPresent()) {
			recipeRepository.deleteById(id);
			RM_LOGGER.debug("Recipe Deleted: \n{}", recipe.get());
			return recipe.get();
		} else {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
