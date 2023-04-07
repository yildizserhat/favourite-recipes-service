package com.yildiz.serhat.favouriterecipesservice.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredientCreateRequestDTO(
        @NotNull(message = "Ingredient name is required")
        @Size(min = 2, max = 200, message
                = "Ingredient must be between 10 and 200 characters")
        String ingredient
) {
}
