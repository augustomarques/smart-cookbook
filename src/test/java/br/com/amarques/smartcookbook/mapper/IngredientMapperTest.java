package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(ingredient.getName()).isEqualTo(dto.name);
        assertThat(ingredient.getRecipe()).isEqualTo(recipe);
    }

    @Test
    void should_convert_entity_to_dto() {
        final var recipe = new Recipe();
        final var ingredient = new Ingredient(recipe);
        ingredient.setId(1L);
        ingredient.setName("Rice");

        final var ingredientDTO = IngredientMapper.toDTO(ingredient);

        assertNotNull(ingredientDTO);
        assertThat(ingredientDTO.id).isEqualTo(ingredient.getId());
        assertThat(ingredientDTO.name).isEqualTo(ingredient.getName());
    }
}
