package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.IngredientDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.mapper.IngredientMapper;
import br.com.amarques.smartcookbook.usecase.ingredient.CreateIngredientUseCase;
import br.com.amarques.smartcookbook.usecase.ingredient.DeleteIngredientUseCase;
import br.com.amarques.smartcookbook.usecase.ingredient.GetIngredientUseCase;
import br.com.amarques.smartcookbook.usecase.ingredient.UpdateIngredientUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IngredientResource.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IngredientRecipeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateIngredientUseCase createIngredientUseCase;
    @MockBean
    private UpdateIngredientUseCase updateIngredientUseCase;
    @MockBean
    private GetIngredientUseCase getIngredientUseCase;
    @MockBean
    private DeleteIngredientUseCase deleteIngredientUseCase;

    @Test
    void should_create() throws Exception {
        final var recipeId = 222L;
        final var ingredientId = 222L;
        final var ingredientDTO = new CreateUpdateIngredientDTO("Garlic");

        when(createIngredientUseCase.create(recipeId, ingredientDTO)).thenReturn(new SimpleEntityDTO(ingredientId));

        final var mvcResult = mockMvc.perform(post("/api/recipes/{recipeId}/ingredients", recipeId)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ingredientDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        final var simpleEntityDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                SimpleEntityDTO.class);

        assertNotNull(simpleEntityDTO);
        assertThat(simpleEntityDTO.id, is(equalTo(recipeId)));
    }

    @Test
    void should_return_ingredient_when_find_by_id() throws Exception {
        final var recipeId = 1L;
        final var ingredientId = 20L;
        final var ingredient = new Ingredient(new Recipe());
        ingredient.setId(ingredientId);

        when(getIngredientUseCase.get(recipeId, ingredientId)).thenReturn(IngredientMapper.toDTO(ingredient));

        final var mvcResult = mockMvc.perform(get("/api/recipes/{recipeId}/ingredients/{ingredientId}",
                recipeId, ingredientId))
                .andExpect(status().isOk())
                .andReturn();

        final var ingredientDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                IngredientDTO.class);

        assertNotNull(ingredientDTO);
        assertThat(ingredientDTO.id, is(equalTo(ingredientId)));
    }

    @Test
    void should_return_all_ingredients_from_recipe_id() throws Exception {
        final var recipeId = 1L;
        final var ingredientId = 20L;
        final var ingredient = new Ingredient(new Recipe());
        ingredient.setId(ingredientId);

        when(getIngredientUseCase.getAll(recipeId)).thenReturn(List.of(IngredientMapper.toDTO(ingredient),
                IngredientMapper.toDTO(ingredient)));

        final var mvcResult = mockMvc.perform(get("/api/recipes/{recipeId}/ingredients", recipeId))
                .andExpect(status().isOk())
                .andReturn();

        final List<IngredientDTO> ingredientsDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, IngredientDTO.class));

        assertNotNull(ingredientsDTO);
        assertThat(ingredientsDTO.size(), is(equalTo(2)));
    }

    @Test
    void should_update() throws Exception {
        final var recipeId = 123L;
        final var ingredientId = 456L;
        final var dto = new CreateUpdateIngredientDTO("changed name");

        mockMvc.perform(put("/api/recipes/{recipeId}/ingredients/{ingredientId}", recipeId, ingredientId)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(updateIngredientUseCase).update(recipeId, ingredientId, dto);
    }

    @Test
    void should_delete() throws Exception {
        final var recipeId = 123L;
        final var ingredientId = 456L;

        mockMvc.perform(delete("/api/recipes/{recipeId}/ingredients/{ingredientId}", recipeId, ingredientId)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(deleteIngredientUseCase).delete(recipeId, ingredientId);
    }
}
