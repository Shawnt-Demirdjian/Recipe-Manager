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
import org.springframework.data.mongodb.MongoDatabaseFactory;
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
	private final MongoDatabaseFactory mongo;

	private static final Logger rmLogger = LoggerFactory.getLogger(WebController.class);

	@Autowired
	public WebController(MongoDatabaseFactory mongo) {
		this.mongo = mongo;
	}

	/**
	 * Returns the recipe associated with the provided ID.
	 *
	 * @param id
	 * @return Recipe
	 * @return 404/null, No recipe with provided ID found
	 */
	@GetMapping("/recipes/{id}")
	public Recipe getRecipe(@PathVariable("id") @NotBlank String id, HttpServletResponse httpResponse) {
		Optional<Recipe> response = recipeRepository.findById(id);

		if (response.isPresent()) {
			rmLogger.debug("Recipe Found: \n{}", response.get());
			return response.get();
		} else {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			rmLogger.debug("No Recipe Found.");
			return null;
		}
	}

	/**
	 * Returns the recipes that have the query string in their title or description
	 *
	 * @param queryString
	 * @return List<Recipe>
	 * @return 404/null, No recipes found
	 */
	@GetMapping("/recipes")
	public List<Recipe> getRecipes(@RequestParam("queryString") @NotBlank String queryString,
			HttpServletResponse httpResponse) {
		List<Recipe> response = recipeRepository.searchByTitleAndDescription(queryString);
		if (response.isEmpty()) {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			rmLogger.debug("No Recipes Found.");
		} else {
			rmLogger.debug("Recipes Found: \n{}", response);
		}
		return response;
	}

	/**
	 * Creates a new recipe with the provided values.
	 *
	 * @param newRecipe
	 * @return 201, Created Successfully
	 * @return 400, Missing required values, etc.
	 */
	@PostMapping("/recipes")
	public void createRecipe(@Valid @RequestBody CreateRecipeBody newRecipe, HttpServletResponse httpResponse) {
		Recipe recipe = new Recipe();
		recipe.setDescription(newRecipe.getDescription());
		recipe.setTitle(newRecipe.getTitle());
		recipe.setIngredients(newRecipe.getIngredients());
		recipe.setSteps(newRecipe.getSteps());
		recipeRepository.save(recipe);
		rmLogger.debug("Recipe Created: \n{}", recipe);
	}

	/**
	 * Updates the recipe of the provided ID with the provided values
	 *
	 * @param newRecipeParts
	 * @return 200, Successfully updated
	 * @return 400, Missing ID or values
	 */
	@PatchMapping("/recipes/{id}")
	public void updateRecipe(@PathVariable("id") @NotBlank String id,
			@Valid @RequestBody UpdateRecipeBody newRecipeParts, HttpServletResponse httpResponse) {
		// Check if recipe with id exists
		Optional<Recipe> existingRecipe = recipeRepository.findById(id);
		if (!existingRecipe.isPresent()) {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			rmLogger.debug("No Recipe Found.");
			return;
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

		// Update the recipe
		recipeRepository.save(newRecipe);
		rmLogger.debug("Recipe Updated: \n{}", newRecipe);
	}

	/**
	 * Deletes the recipe associated with the provided ID.
	 *
	 * @param id
	 * @return 200, Successfully deleted
	 * @return 404, No recipe with provided ID found
	 */
	@DeleteMapping("/recipes/{id}")
	public void deleteRecipe(@PathVariable("id") @NotBlank String id, HttpServletResponse httpResponse) {
		if (recipeRepository.existsById(id)) {
			recipeRepository.deleteById(id);
			rmLogger.debug("Recipe Deleted with ID: {}", id);
		} else {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
