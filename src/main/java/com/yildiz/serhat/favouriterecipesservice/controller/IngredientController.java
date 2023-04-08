package com.yildiz.serhat.favouriterecipesservice.controller;

import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;
import com.yildiz.serhat.favouriterecipesservice.service.IngredientService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/ingredients")
@RequiredArgsConstructor
@Tag(name = "Ingredient", description = "Endpoints about Ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    @Operation(summary = "Create Ingredient")
    public ResponseEntity<Void> createIngredient(@RequestBody @Valid IngredientCreateRequestDTO request) {
        ingredientService.createIngredient(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get All Ingredient")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return new ResponseEntity<>(ingredientService.getAllIngredients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Ingredient By id")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ingredientService.getIngredientById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Ingredient By id")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable("id") Long id) {
        ingredientService.deleteIngredientById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Ingredient By id")
    public ResponseEntity<Ingredient> updateIngredientById(@PathVariable("id") Long id, @RequestBody @Valid IngredientCreateRequestDTO request) {
        return new ResponseEntity<>(ingredientService.updateIngredientById(id, request), HttpStatus.OK);
    }
}
