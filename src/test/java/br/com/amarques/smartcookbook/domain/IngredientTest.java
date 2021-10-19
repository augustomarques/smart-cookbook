package br.com.amarques.smartcookbook.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IngredientTest {

    @Test
    void should_create_ingredient() {
        final var recipe = new Recipe();

        final var riceIngredient = new Ingredient(recipe);
        riceIngredient.setId(1L);
        riceIngredient.setName("Rice");

        final var garlicIngredient = new Ingredient(recipe);
        garlicIngredient.setId(2L);
        garlicIngredient.setName("Garlic");

        assertNotNull(recipe);
        assertNotNull(riceIngredient);
        assertNotNull(garlicIngredient);

        assertThat(riceIngredient.getId()).isEqualTo(1L);
        assertThat(riceIngredient.getName()).isEqualTo("Rice");
        assertThat(riceIngredient.getRecipe()).isEqualTo(recipe);

        assertThat(garlicIngredient.getId()).isEqualTo(2L);
        assertThat(garlicIngredient.getName()).isEqualTo("Garlic");
        assertThat(garlicIngredient.getRecipe()).isEqualTo(recipe);
    }
}
