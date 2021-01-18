package com.demirdjian.recipemanager.db;

import java.util.List;

import com.demirdjian.recipemanager.models.Category;
import com.demirdjian.recipemanager.models.CookingMethod;
import com.demirdjian.recipemanager.models.Recipe;

public interface RecipeRepositoryCustom {

    /**
     * Perform a Full Text Search on the Title and Description. Optionally search by
     * Category and Cooking Method as well.
     * 
     * @param queryText
     * @param category
     * @param cookingMethod
     * @return List<Recipe>
     */
    public List<Recipe> search(String queryText, Category category, CookingMethod cookingMethod);
}
