package com.yildiz.serhat.favouriterecipesservice.service.impl;

import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.controller.request.RecipeCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Recipe;
import com.yildiz.serhat.favouriterecipesservice.domain.RecipeType;
import com.yildiz.serhat.favouriterecipesservice.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;
    @Mock
    private IngredientServiceImpl ingredientService;
    @Mock
    private RecipeRepository repository;

    @Test
    public void shouldCreateRecipe() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");

        RecipeCreateRequestDTO requestDTO =
                new RecipeCreateRequestDTO("oven", "Serhat's Recipe", "vegetarian", 5, "30 min", Set.of(1L));

        when(repository.save(any())).thenReturn(Recipe.builder().id(3L).build());

        recipeService.createRecipe(requestDTO);

        verify(ingredientService, atLeastOnce()).getIngredientById(1L);
        verify(repository, atLeastOnce()).save(any());
    }


    @Test
    public void shouldGetRecipeById() {
        when(repository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).build()));

        Recipe recipe = recipeService.getRecipeById(1L);

        assertEquals(recipe.getId(), 1L);
    }

    @Test
    public void shouldUpdateRecipe() {
        RecipeCreateRequestDTO requestDTO =
                new RecipeCreateRequestDTO("oven", "Serhat's Recipe", "vegetarian", 5, "30 min", Set.of(1L));
        when(repository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).build()));
        when(repository.save(any())).thenReturn(Recipe.buildRecipeFromRequest(requestDTO));
        Recipe recipe = recipeService.updateRecipeById(1L, requestDTO);

        assertEquals("oven", recipe.getInstruction());
        assertEquals("Serhat's Recipe", recipe.getName());
        assertEquals(RecipeType.VEGETARIAN, recipe.getType());
        assertEquals(5, recipe.getNumberOfServings());
        assertEquals("30 min", recipe.getPreparationTime());
    }

    @Test
    public void shouldDeleteRecipe() {
        when(repository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).build()));

        recipeService.deleteRecipeById(1L);

        verify(repository, atLeastOnce()).delete(any());

    }

}