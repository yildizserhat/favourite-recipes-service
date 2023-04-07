package com.yildiz.serhat.favouriterecipesservice.domain;


import java.util.Arrays;

public enum RecipeType {
    VEGAN("vegan"),
    VEGETARIAN("vegetarian"),
    OTHER("other");

    private String message;

    RecipeType(String message) {
        this.message = message;
    }

    public static RecipeType getByValue(String value) {
        return Arrays.stream(RecipeType.values())
                .filter(type -> type.getMessage().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getMessage() {
        return message;
    }
}
