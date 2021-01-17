package com.demirdjian.recipemanager.db;

import java.util.List;

import com.demirdjian.recipemanager.models.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    @Autowired
    private MongoTemplate template;

    @Override
    public List<Recipe> searchByTitleAndDescription(String queryText) {
        // Split queryText on whitespace
        String[] queryArr = queryText.split("\\s+");
        // Full Text Search on now split terms
        TextCriteria criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).matchingAny(queryArr);
        Query query = TextQuery.queryText(criteria).sortByScore();
        return template.find(query, Recipe.class);
    }

}
