package com.yildiz.serhat.favouriterecipesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
