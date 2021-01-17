package com.demirdjian.recipemanager.db;

import java.util.List;

import com.demirdjian.recipemanager.models.Recipe;

public interface RecipeRepositoryCustom {

    /**
     * Perform a Full Text Search on the Title and Description.
     * 
     * @param queryText
     * @return List<Recipe>
     */
    public List<Recipe> searchByTitleAndDescription(String queryText);
}
