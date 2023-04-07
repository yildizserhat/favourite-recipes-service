package com.yildiz.serhat.favouriterecipesservice.service;

import com.yildiz.serhat.favouriterecipesservice.controller.request.RecipeCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Recipe;

import java.util.List;

public interface RecipeService {

    void createRecipe(RecipeCreateRequestDTO request);

    List<Recipe> getRecipeWithCriteria(String type, Integer numberOfServings, String ingredient, String instruction);

    Recipe getRecipeById(Long id);

    void deleteRecipeById(Long id);

    Recipe updateRecipeById(Long id, RecipeCreateRequestDTO request);
}
