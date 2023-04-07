package com.yildiz.serhat.favouriterecipesservice.domain;

import com.yildiz.serhat.favouriterecipesservice.controller.request.RecipeCreateRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe")
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "instruction", nullable = false)
    private String instruction;

    @NotNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecipeType type;

    @NotNull
    @Column(name = "number_servings", nullable = false)
    private Integer numberOfServings;

    @NotNull
    @Column(name = "prep_time", nullable = false)
    private String preparationTime;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new HashSet<>();


    public static Recipe buildRecipeFromRequest(RecipeCreateRequestDTO request) {
        return Recipe.builder()
                .name(request.name())
                .instruction(request.instruction())
                .type(RecipeType.getByValue(request.type()))
                .numberOfServings(request.numberOfServings())
                .preparationTime(request.preparationTime())
                .build();
    }

    public static Recipe updateRecipe(Recipe recipe, RecipeCreateRequestDTO request) {
        recipe.setName(request.name());
        recipe.setInstruction(request.instruction());
        recipe.setType(RecipeType.getByValue(request.type()));
        recipe.setNumberOfServings(request.numberOfServings());
        recipe.setPreparationTime(request.preparationTime());
        return recipe;
    }
}
