package com.demirdjian.recipemanager.db;

import java.util.List;

import com.demirdjian.recipemanager.models.Recipe;

public interface RecipeRepositoryCustom {

    public List<Recipe> searchByTitleAndDescription(String queryText);
}
