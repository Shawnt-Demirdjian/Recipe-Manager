package com.demirdjian.recipemanager.db;

import java.util.List;

import com.demirdjian.recipemanager.models.Category;
import com.demirdjian.recipemanager.models.CookingMethod;
import com.demirdjian.recipemanager.models.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    @Autowired
    private MongoTemplate template;

    @Override
    public List<Recipe> search(String queryText, Category category, CookingMethod cookingMethod) {
        // Split queryText on whitespace
        String[] queryArr = queryText.split("\\s+");

        // Full Text Search on now split terms
        TextCriteria criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).matchingAny(queryArr);
        Query query = TextQuery.queryText(criteria).sortByScore();

        // Add criteria for category and cooking method, if provided
        if (category != null) {
            query.addCriteria(Criteria.where("category").is(category));
        }
        if (cookingMethod != null) {
            query.addCriteria(Criteria.where("cookingMethod").is(cookingMethod));
        }
        return template.find(query, Recipe.class);
    }

}
