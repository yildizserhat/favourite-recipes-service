package com.yildiz.serhat.favouriterecipesservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiErrorType {

    // Base error types
    INTERNAL_SERVER_ERROR(1000, "Internal Server Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST_ERROR(1001, "Invalid Request Exception", HttpStatus.BAD_REQUEST),
    FIELD_MISSING_ERROR(1002, "Field Missing Exception", HttpStatus.BAD_REQUEST),
    FIELD_VALIDATION_ERROR(1003, "Field Validation Exception", HttpStatus.BAD_REQUEST),


    //Validation
    RECIPE_NOT_FOUND_EXCEPTION(1005, "Recipe Not Found Exception.", HttpStatus.NOT_FOUND),
    INGREDIENT_NOT_FOUND_EXCEPTION(1006, "Ingredient Not Found Exception.", HttpStatus.NOT_FOUND),
    ILLEGAL_RECIPE_TYPE_EXCEPTION(1008, "Illegal Recipe Type Exception.", HttpStatus.BAD_REQUEST);

    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;
}
