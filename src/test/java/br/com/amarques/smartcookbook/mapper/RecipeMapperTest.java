package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateRecipeDTO;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeMapperTest {

    @Test
    void should_convert_dto_to_entity() {
        final var dto = new CreateUpdateRecipeDTO("White rice", "way to cook white rice");

        final var recipe = RecipeMapper.toEntity(dto);

        assertNotNull(recipe);
        assertNull(recipe.getId());
        assertThat(recipe.getName(), is(equalTo(dto.name)));
        assertThat(recipe.getWayOfDoing(), is(equalTo(dto.wayOfDoing)));
    }

    @Test
    void should_convert_entity_to_dto() {
        final var recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("White rice");
        recipe.setWayOfDoing("way to cook white rice");

        final var recipeDTO = RecipeMapper.toDTO(recipe);

        assertNotNull(recipeDTO);
        assertThat(recipeDTO.id, is(equalTo(recipe.getId())));
        assertThat(recipeDTO.name, is(equalTo(recipe.getName())));
        assertThat(recipeDTO.wayOfDoing, is(equalTo(recipe.getWayOfDoing())));
    }
}
