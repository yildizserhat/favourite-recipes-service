package com.yildiz.serhat.favouriterecipesservice.service.impl;

import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;
import com.yildiz.serhat.favouriterecipesservice.exception.IngredientNotFoundException;
import com.yildiz.serhat.favouriterecipesservice.repository.IngredientRepository;
import com.yildiz.serhat.favouriterecipesservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repository;

    @Override
    public void createIngredient(IngredientCreateRequestDTO request) {
        Ingredient ingredient = Ingredient.buildIngredientFromRequest(request);
        repository.save(ingredient);
        log.info("Ingredient with name: {} and id: {} created", ingredient.getName(), ingredient.getId());
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        log.info("All ingredients are received");
        return repository.findAll();
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        log.info("Ingredient with id: {} is being received", id);
        return repository.findById(id).orElseThrow(()
                -> new IngredientNotFoundException(String.format("Ingredient with id: {%s} not found", id)));
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        log.info("Ingredient with name: {} is being received", name);
        return repository.findByName(name).orElseThrow(()
                -> new IngredientNotFoundException(String.format("Ingredient with name: {%s} not found", name)));
    }

    @Override
    public void deleteIngredientById(Long id) {
        Ingredient ingredient = getIngredientById(id);
        repository.delete(ingredient);
        log.info("Ingredient with id: {} deleted", id);
    }

    @Override
    public Ingredient updateIngredientById(Long id, IngredientCreateRequestDTO request) {
        Ingredient ingredient = getIngredientById(id);
        Ingredient updatedIngredient = Ingredient.updateIngredient(ingredient, request);
        log.info("Ingredient with id: {} updated", id);
        return repository.save(updatedIngredient);
    }
}
