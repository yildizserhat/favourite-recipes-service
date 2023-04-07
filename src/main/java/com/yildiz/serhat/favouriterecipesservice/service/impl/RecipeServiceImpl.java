package com.yildiz.serhat.favouriterecipesservice.service.impl;

import com.yildiz.serhat.favouriterecipesservice.controller.request.RecipeCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;
import com.yildiz.serhat.favouriterecipesservice.domain.Recipe;
import com.yildiz.serhat.favouriterecipesservice.domain.RecipeType;
import com.yildiz.serhat.favouriterecipesservice.exception.RecipeNotFoundException;
import com.yildiz.serhat.favouriterecipesservice.repository.RecipeRepository;
import com.yildiz.serhat.favouriterecipesservice.service.IngredientService;
import com.yildiz.serhat.favouriterecipesservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;
    private final IngredientService ingredientService;

    @Override
    public void createRecipe(RecipeCreateRequestDTO request) {
        Recipe recipe = Recipe.buildRecipeFromRequest(request);
        Set<Ingredient> collect = request.ingredients().stream().map(ingredientService::getIngredientById)
                .collect(Collectors.toSet());

        recipe.setIngredients(collect);
        Recipe savedRecipe = repository.save(recipe);
        log.info(String.format("Recipe with id: {%s} created", savedRecipe.getId()));
    }

    @Override
    public List<Recipe> getRecipeWithCriteria(String type, Integer numberOfServings, String ingredient, String instruction) {
        Recipe recipe = Recipe.builder()
                .numberOfServings(numberOfServings)
                .instruction(instruction)
                .build();

        if (StringUtils.isNotBlank(type)) {
            recipe.setType(RecipeType.getByValue(type));
        }

        List<Recipe> recipes = repository.findAll(Example.of(recipe));

        if (StringUtils.isNotBlank(ingredient)) {
            Ingredient ingredientByName = ingredientService.getIngredientByName(ingredient);
            recipes = recipes.stream().filter(y -> y.getIngredients().contains(ingredientByName)).toList();
        }
        return recipes;
    }


    @Override
    public Recipe getRecipeById(Long id) {
        log.info(String.format("Getting recipe with id: {%s}", id));
        return repository.findById(id).orElseThrow(()
                -> new RecipeNotFoundException(String.format("Recipe with id: {%s} not found", id)));
    }

    @Override
    public void deleteRecipeById(Long id) {
        Recipe recipe = getRecipeById(id);
        repository.delete(recipe);
        log.info(String.format("Recipe with id: {%s} deleted", id));
    }

    @Override
    public Recipe updateRecipeById(Long id, RecipeCreateRequestDTO request) {
        Recipe recipe = getRecipeById(id);
        Recipe updatedRecipe = Recipe.updateRecipe(recipe, request);
        Set<Ingredient> ingredients = request.ingredients().stream().map(ingredientService::getIngredientById)
                .collect(Collectors.toSet());
        updatedRecipe.setIngredients(ingredients);
        log.info(String.format("Recipe with id: {%s} updated", id));
        return repository.save(updatedRecipe);
    }
}
