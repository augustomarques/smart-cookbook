package br.com.amarques.smartcookbook.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeTest {

    @Test
    void shouldCreateRecipe() {
        final var recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test name");
        recipe.setWayOfDoing("Way of doing test");

        assertNotNull(recipe);
        assertThat(recipe.getId(), is(equalTo(1L)));
        assertThat(recipe.getName(), is(equalTo("Test name")));
        assertThat(recipe.getWayOfDoing(), is(equalTo("Way of doing test")));
    }
}
