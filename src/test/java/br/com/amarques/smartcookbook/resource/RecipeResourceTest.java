package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.RecipeDTO;
import br.com.amarques.smartcookbook.dto.rest.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.mapper.RecipeMapper;
import br.com.amarques.smartcookbook.usecase.recipe.CreateRecipeUseCase;
import br.com.amarques.smartcookbook.usecase.recipe.DeleteRecipeUseCase;
import br.com.amarques.smartcookbook.usecase.recipe.GetRecipeUseCase;
import br.com.amarques.smartcookbook.usecase.recipe.UpdateRecipeUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RecipeResource.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateRecipeUseCase createRecipeUseCase;
    @MockBean
    private UpdateRecipeUseCase updateRecipeUseCase;
    @MockBean
    private GetRecipeUseCase getRecipeUseCase;
    @MockBean
    private DeleteRecipeUseCase deleteRecipeUseCase;

    @Test
    void should_return_all_recipes_registered() throws Exception {
        final var recipe1 = new Recipe();
        recipe1.setId(1L);

        final var recipe2 = new Recipe();
        recipe2.setId(2L);

        when(getRecipeUseCase.getAll(PageRequest.of(0, 10))).thenReturn(List.of(RecipeMapper.toDTO(recipe1),
                RecipeMapper.toDTO(recipe2)));

        final var mvcResult = mockMvc.perform(get("/api/recipes"))
            .andExpect(status().isOk())
            .andReturn();

        final List<RecipeDTO> recipes = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
            TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, RecipeDTO.class));

        assertNotNull(recipes);
        assertEquals(2, recipes.size());
    }

    @Test
    void should_return_recipe_when_find_by_id() throws Exception {
        final var id = 1L;
        final var recipe1 = new Recipe();
        recipe1.setId(id);

        when(getRecipeUseCase.get(id)).thenReturn(RecipeMapper.toDTO(recipe1));

        final var mvcResult = mockMvc.perform(get("/api/recipes/{id}", 1L))
            .andExpect(status().isOk())
            .andReturn();

        final var recipe = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), RecipeDTO.class);

        assertNotNull(recipe);
        assertThat(recipe.id, is(equalTo(id)));
    }

    @Test
    void should_create() throws Exception {
        final var id = 222L;
        final var recipeDTO = new CreateUpdateRecipeDTO("White Rice", "Rice preparation mode");
        when(createRecipeUseCase.create(recipeDTO)).thenReturn(new SimpleEntityDTO(id));

        final var mvcResult = mockMvc.perform(post("/api/recipes")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(recipeDTO)))
            .andExpect(status().isCreated())
            .andReturn();

        final var simpleEntityDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
            SimpleEntityDTO.class);

        assertNotNull(simpleEntityDTO);
        assertThat(simpleEntityDTO.id, is(equalTo(id)));
    }

    @Test
    void should_update() throws Exception {
        final var id = 123L;
        final var dto = new CreateUpdateRecipeDTO("name", "way of doing");

        mockMvc.perform(put("/api/recipes/{id}", id)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());

        verify(updateRecipeUseCase).update(id, dto);
    }

    @Test
    void should_find_by_ingredients() throws Exception {
        final var riceRecipe = new RecipeDTO(111L, "Rice", "way to cook white rice");
        final var beanRecipe = new RecipeDTO(222L, "Beans", "way to cook white beans");

        when(getRecipeUseCase.findByIngredients(List.of("Ingredient1", "Ingredient2"))).thenReturn(List.of(riceRecipe, beanRecipe));

        final var mvcResult = mockMvc.perform(get("/api/recipes/search?ingredients=Ingredient1,Ingredient2"))
                .andExpect(status().isOk())
                .andReturn();

        final List<RecipeDTO> recipes = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
            TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, RecipeDTO.class));

        assertNotNull(recipes);
        assertThat(recipes.size(), is(equalTo(2)));
    }

    @Test
    void should_delete() throws Exception {
        final var recipeId = 123L;

        mockMvc.perform(delete("/api/recipes/{recipeId}", recipeId)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(deleteRecipeUseCase).delete(recipeId);
    }
}
