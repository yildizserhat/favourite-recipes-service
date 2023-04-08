package com.yildiz.serhat.favouriterecipesservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient")
public class Ingredient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "ingredients", cascade = CascadeType.MERGE)
    private List<Recipe> recipe = new ArrayList<>();


    public static Ingredient buildIngredientFromRequest(IngredientCreateRequestDTO requestDTO) {
        return Ingredient.builder()
                .name(requestDTO.ingredient())
                .build();
    }

    public static Ingredient updateIngredient(Ingredient ingredient, IngredientCreateRequestDTO requestDTO) {
        ingredient.setName(requestDTO.ingredient());
        return ingredient;
    }
}
