package com.yildiz.serhat.favouriterecipesservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yildiz.serhat.favouriterecipesservice.TestInitializer;
import com.yildiz.serhat.favouriterecipesservice.controller.request.IngredientCreateRequestDTO;
import com.yildiz.serhat.favouriterecipesservice.domain.Ingredient;
import com.yildiz.serhat.favouriterecipesservice.repository.IngredientRepository;
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

import java.util.List;

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
class IngredientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void setUp() {
        ingredientRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        ingredientRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void shouldCreateIngredient() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");

        String url = "/v1/ingredients";

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientCreateRequestDTO)))
                .andExpect(status().isCreated());

        List<Ingredient> all = ingredientRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("Salt", all.get(0).getName());
    }

    @Test
    @SneakyThrows
    void shouldGetIngredientById() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");
        ingredientRepository.save(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO));

        String url = "/v1/ingredients/1";
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.updatedDate").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Salt"));

    }

    @Test
    @SneakyThrows
    void shouldGetAllIngredients() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO1 = new IngredientCreateRequestDTO("Salt");
        IngredientCreateRequestDTO ingredientCreateRequestDTO2 = new IngredientCreateRequestDTO("Pepper");

        ingredientRepository.save(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO1));
        ingredientRepository.save(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO2));

        String url = "/v1/ingredients";
        MvcResult mvcResult = mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Ingredient> ingredients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Ingredient.class));

        assertEquals(2, ingredients.size());
        assertEquals("Salt", ingredients.get(0).getName());
        assertEquals("Pepper", ingredients.get(1).getName());
    }

    @Test
    @SneakyThrows
    void shouldDeleteIngredientById() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");
        ingredientRepository.save(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO));

        String url = "/v1/ingredients/1";
        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Ingredient> all = ingredientRepository.findAll();

        assertEquals(0, all.size());

    }

    @Test
    @SneakyThrows
    void shouldThrowExceptionWhenIdNotFound() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");
        ingredientRepository.save(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO));

        String url = "/v1/ingredients/555";
        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        List<Ingredient> all = ingredientRepository.findAll();

        assertEquals(1, all.size());
    }

    @Test
    @SneakyThrows
    void shouldUpdateIngredient() {
        IngredientCreateRequestDTO ingredientCreateRequestDTO = new IngredientCreateRequestDTO("Salt");
        ingredientRepository.save(Ingredient.buildIngredientFromRequest(ingredientCreateRequestDTO));

        IngredientCreateRequestDTO updateDTO = new IngredientCreateRequestDTO("Salt");

        String url = "/v1/ingredients/1";
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());

        List<Ingredient> all = ingredientRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("Salt", all.get(0).getName());
    }


}