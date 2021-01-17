package com.demirdjian.recipemanager.db;

import java.util.Optional;

import com.demirdjian.recipemanager.models.Recipe;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String>, RecipeRepositoryCustom {

    /**
     * Find a recipe by it's ID.
     * 
     * @param id
     * @return Optional <Recipe>
     */
    public Optional<Recipe> findById(String id);
}
