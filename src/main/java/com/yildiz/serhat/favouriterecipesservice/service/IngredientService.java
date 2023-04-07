package com.yildiz.serhat.favouriterecipesservice.service;

import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;

import java.util.List;

public interface IngredientService {

    void createIngredient(IngredientCreateRequestDTO request);

    List<Ingredient> getAllIngredients();

    Ingredient getIngredientById(Long id);

    Ingredient getIngredientByName(String name);

    void deleteIngredientById(Long id);

    Ingredient updateIngredientById(Long id, IngredientCreateRequestDTO request);
}
