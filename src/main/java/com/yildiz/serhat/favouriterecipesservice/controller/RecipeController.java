package com.yildiz.serhat.favouriterecipesservice.controller;


import com.yildiz.serhat.favouriterecipesservice.controller.request.RecipeCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Recipe;
import com.yildiz.serhat.favouriterecipesservice.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipe", description = "Endpoints about Recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    @Operation(summary = "Create Recipe By id")
    public ResponseEntity<Void> createRecipe(@RequestBody @Valid RecipeCreateRequestDTO request) {
        recipeService.createRecipe(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get Recipe By id")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Recipe By id")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable("id") Long id) {
        recipeService.deleteRecipeById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Recipe By id")
    public ResponseEntity<Recipe> updateRecipeById(@PathVariable("id") Long id, @RequestBody @Valid RecipeCreateRequestDTO request) {
        return new ResponseEntity<>(recipeService.updateRecipeById(id, request), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Search Recipe with criteria")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(value = "type", required = false) String type,
                                                     @RequestParam(value = "numberOfServings", required = false) Integer numberOfServings,
                                                     @RequestParam(value = "ingredient", required = false) String ingredient,
                                                     @RequestParam(value = "instruction", required = false) String instruction
    ) {
        return new ResponseEntity<>(recipeService.getRecipeWithCriteria(type, numberOfServings, ingredient, instruction), HttpStatus.OK);
    }

}
