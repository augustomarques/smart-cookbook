package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IngredientMapperTest {

    @Test
    void should_convert_dto_to_entity() {
        final var dto = new CreateUpdateIngredientDTO("Rice");
        final var recipe = new Recipe();

        final var ingredient = IngredientMapper.toEntity(dto, recipe);

        assertNotNull(ingredient);
        assertNull(ingredient.getId());
        assertThat(ingredient.getName(), is(equalTo(dto.name)));
        assertThat(ingredient.getRecipe(), is(equalTo(recipe)));
    }

    @Test
    void should_convert_entity_to_dto() {
        final var recipe = new Recipe();
        final var ingredient = new Ingredient(recipe);
        ingredient.setId(1L);
        ingredient.setName("Rice");

        final var ingredientDTO = IngredientMapper.toDTO(ingredient);

        assertNotNull(ingredientDTO);
        assertThat(ingredientDTO.id, is(equalTo(ingredient.getId())));
        assertThat(ingredientDTO.name, is(equalTo(ingredient.getName())));
    }
}
