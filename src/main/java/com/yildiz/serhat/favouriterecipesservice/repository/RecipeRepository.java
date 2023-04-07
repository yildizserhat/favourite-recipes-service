package com.yildiz.serhat.favouriterecipesservice.repository;

import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;
import com.yildiz.serhat.favouriterecipesservice.domain.Recipe;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Hidden
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByIngredients(Ingredient ingredients);
}
