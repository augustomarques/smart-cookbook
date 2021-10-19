package br.com.amarques.smartcookbook.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeTest {

    @Test
    void should_create_recipe() {
        final var recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test name");
        recipe.setWayOfDoing("Way of doing test");

        assertNotNull(recipe);
        assertThat(recipe.getId()).isEqualTo(1L);
        assertThat(recipe.getName()).isEqualTo("Test name");
        assertThat(recipe.getWayOfDoing()).isEqualTo("Way of doing test");
    }

}
