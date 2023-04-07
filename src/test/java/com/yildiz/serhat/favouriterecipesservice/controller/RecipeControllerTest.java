package com.yildiz.serhat.favouriterecipesservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yildiz.serhat.favouriterecipesservice.TestInitializer;
import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.controller.request.RecipeCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;
import com.yildiz.serhat.favouriterecipesservice.domain.Recipe;
import com.yildiz.serhat.favouriterecipesservice.domain.RecipeType;
import com.yildiz.serhat.favouriterecipesservice.repository.IngredientRepository;
import com.yildiz.serhat.favouriterecipesservice.repository.RecipeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(initializers = TestInitializer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        IngredientCreateRequestDTO salt = new IngredientCreateRequestDTO("Salt");
        IngredientCreateRequestDTO pepper = new IngredientCreateRequestDTO("Pepper");
        IngredientCreateRequestDTO tomato = new IngredientCreateRequestDTO("Tomato");

        Ingredient ingredient1 = Ingredient.buildIngredientFromRequest(salt);
        Ingredient ingredient2 = Ingredient.buildIngredientFromRequest(pepper);
        Ingredient ingredient3 = Ingredient.buildIngredientFromRequest(tomato);

        ingredientRepository.saveAll(Arrays.asList(ingredient1, ingredient2, ingredient3));
    }

    @AfterEach
    void tearDown() {
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void shouldCreateRecipe() {
        RecipeCreateRequestDTO requestDTO =
                new RecipeCreateRequestDTO("oven", "Serhat's Recipe", "vegetarian", 5, "30 min", Set.of(1L, 2L, 3L));
        String url = "/v1/recipes";

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());

        List<Recipe> all = recipeRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("Serhat's Recipe", all.get(0).getName());
        assertEquals("oven", all.get(0).getInstruction());
        assertEquals("30 min", all.get(0).getPreparationTime());
        assertEquals(RecipeType.VEGETARIAN, all.get(0).getType());
        assertEquals(5, all.get(0).getNumberOfServings().intValue());
    }

    @Test
    @SneakyThrows
    void shouldGetRecipeById() {
        RecipeCreateRequestDTO requestDTO =
                new RecipeCreateRequestDTO("oven", "Serhat's Recipe",
                        "vegetarian", 5, "30 min", Set.of(1L, 2L, 3L));
        String url = "/v1/recipes/1";

        recipeRepository.save(Recipe.buildRecipeFromRequest(requestDTO));

        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.instruction").exists())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.numberOfServings").exists())
                .andExpect(jsonPath("$.preparationTime").exists())
                .andExpect(jsonPath("$.ingredients").exists())
                .andExpect(jsonPath("$.instruction").value("oven"))
                .andExpect(jsonPath("$.numberOfServings").value(5))
                .andExpect(jsonPath("$.name").value("Serhat's Recipe"))
                .andExpect(jsonPath("$.preparationTime").value("30 min"));
    }

    @Test
    @SneakyThrows
    void shouldDeleteRecipeById() {
        RecipeCreateRequestDTO requestDTO =
                new RecipeCreateRequestDTO("oven", "Serhat's Recipe",
                        "vegetarian", 5, "30 min", Set.of(1L, 2L, 3L));
        String url = "/v1/recipes/1";

        recipeRepository.save(Recipe.buildRecipeFromRequest(requestDTO));

        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        List<Recipe> all = recipeRepository.findAll();

        assertEquals(0, all.size());
    }

    @Test
    @SneakyThrows
    void shouldThrowExceptionWhenRecipeNotFound() {
        RecipeCreateRequestDTO requestDTO =
                new RecipeCreateRequestDTO("oven", "Serhat's Recipe",
                        "vegetarian", 5, "30 min", Set.of(1L, 2L, 3L));
        String url = "/v1/recipes/555";

        recipeRepository.save(Recipe.buildRecipeFromRequest(requestDTO));

        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());

        List<Recipe> all = recipeRepository.findAll();

        assertEquals(1, all.size());
    }

    @Test
    @SneakyThrows
    void shouldUpdateRecipe() {
        RecipeCreateRequestDTO requestDTO =
                new RecipeCreateRequestDTO("oven", "Serhat's Recipe", "vegetarian", 5, "30 min", Set.of(1L, 2L, 3L));
        String url = "/v1/recipes/1";

        recipeRepository.save(Recipe.buildRecipeFromRequest(requestDTO));

        RecipeCreateRequestDTO updateRequest =
                new RecipeCreateRequestDTO("cooker", "Serhat's Recipe", "vegetarian", 1, "10 min", Set.of(1L, 2L, 3L));

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        List<Recipe> all = recipeRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("Serhat's Recipe", all.get(0).getName());
        assertEquals("cooker", all.get(0).getInstruction());
        assertEquals("10 min", all.get(0).getPreparationTime());
        assertEquals(RecipeType.VEGETARIAN, all.get(0).getType());
        assertEquals(1, all.get(0).getNumberOfServings().intValue());
    }

    @Test
    @SneakyThrows
    void shouldSearchWithCriteria() {
        RecipeCreateRequestDTO recipeCreateRequestDTO1 =
                new RecipeCreateRequestDTO("prepare with oven", "Serhat's 1st Recipe", "other", 1, "30 min", Set.of(1L, 2L, 3L));

        RecipeCreateRequestDTO recipeCreateRequestDTO2 =
                new RecipeCreateRequestDTO("airfryer", "Serhat's 2nd Recipe", "vegan", 1, "30 min", Set.of(1L));

        RecipeCreateRequestDTO recipeCreateRequestDTO3 =
                new RecipeCreateRequestDTO("cooker", "Serhat's 3rd Recipe", "vegetarian", 5, "30 min", Set.of(2L));

        recipeRepository.save(Recipe.buildRecipeFromRequest(recipeCreateRequestDTO1));
        recipeRepository.save(Recipe.buildRecipeFromRequest(recipeCreateRequestDTO2));
        recipeRepository.save(Recipe.buildRecipeFromRequest(recipeCreateRequestDTO3));

        String url = "/v1/recipes?type=vegetarian";
        MvcResult mvcResult = mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<Recipe> recipes1 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Recipe.class));

        assertEquals(1, recipes1.size());
        assertEquals("Serhat's 3rd Recipe", recipes1.get(0).getName());
        assertEquals("cooker", recipes1.get(0).getInstruction());

        String url2 = "/v1/recipes?numberOfServings=1&instruction=airfryer";
        MvcResult mvcResult2 = mvc.perform(get(url2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        List<Recipe> recipes2 = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Recipe.class));

        assertEquals(1, recipes2.size());
        assertEquals("Serhat's 2nd Recipe", recipes2.get(0).getName());
    }

}