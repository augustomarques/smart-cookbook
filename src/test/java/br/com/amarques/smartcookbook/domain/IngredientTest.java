package br.com.amarques.smartcookbook.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IngredientTest {

    @Test
    void shouldCreateIngredient() {
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

        assertThat(riceIngredient.getId(), is(equalTo(1L)));
        assertThat(riceIngredient.getName(), is(equalTo("Rice")));
        assertThat(riceIngredient.getRecipe(), is(equalTo(recipe)));

        assertThat(garlicIngredient.getId(), is(equalTo(2L)));
        assertThat(garlicIngredient.getName(), is(equalTo("Garlic")));
        assertThat(garlicIngredient.getRecipe(), is(equalTo(recipe)));
    }
}
