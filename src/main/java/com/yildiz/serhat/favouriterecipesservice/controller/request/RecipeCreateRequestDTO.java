package com.yildiz.serhat.favouriterecipesservice.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RecipeCreateRequestDTO(
        @NotNull(message = "Instruction cannot be null")
        @Size(min = 2, max = 200, message
                = "Instruction must be between 10 and 200 characters")
        String instruction,
        @NotNull(message = "Name cannot be null")
        @Size(min = 2, max = 200, message
                = "Name must be between 10 and 200 characters")
        String name,
        @NotNull(message = "Type cannot be null")
        String type,
        @NotNull(message = "Number Of Servings cannot be null")
        @Min(value = 1, message = "Number of Serving should not be less than 1")
        @Max(value = 20, message = "Number of Serving should not be greater than 20")
        int numberOfServings,
        @NotNull(message = "Prep Time cannot be null")
        String preparationTime,
        @NotNull(message = "Ingredients cannot be null")
        Set<@NotNull Long> ingredients
) {
}
