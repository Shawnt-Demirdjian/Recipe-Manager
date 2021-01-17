package com.demirdjian.recipemanager.db;

import java.util.List;
import java.util.Optional;

import com.demirdjian.recipemanager.models.Recipe;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String>, RecipeRepositoryCustom {

    public Optional<Recipe> findById(String id);

    public List<Recipe> findByTitle(String title);
}
