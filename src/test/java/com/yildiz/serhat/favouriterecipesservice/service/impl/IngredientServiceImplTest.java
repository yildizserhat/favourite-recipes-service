package com.yildiz.serhat.favouriterecipesservice.service.impl;

import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;
import com.yildiz.serhat.favouriterecipesservice.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @InjectMocks
    private IngredientServiceImpl ingredientService;
    @Mock
    private IngredientRepository repository;

    @Test
    void shouldCreateIngredient() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");

        when(repository.save(any())).thenReturn(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO));

        ingredientService.createIngredient(ingredientCreateRequestDTO);

        verify(repository, atLeastOnce()).save(any());
    }

    @Test
    void shouldGetIngredientById() {
        when(repository.findById(1L)).thenReturn(Optional.of(Ingredient.builder().id(1L).build()));

        Ingredient ingredient = ingredientService.getIngredientById(1L);

        assertEquals(ingredient.getId(), 1L);
    }

    @Test
    void shouldDeleteIngredient() {
        when(repository.findById(1L)).thenReturn(Optional.of(Ingredient.builder().id(1L).build()));

        ingredientService.deleteIngredientById(1L);

        verify(repository, atLeastOnce()).delete(any());
    }

    @Test
    void shouldUpdateIngredient() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");

        when(repository.findById(1L)).thenReturn(Optional.of(Ingredient.builder().id(1L).build()));
        when(repository.save(any())).thenReturn(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO));

        Ingredient ingredient = ingredientService.updateIngredientById(1L, ingredientCreateRequestDTO);

        assertEquals(ingredient.getName(), "Salt");
    }


}