package br.com.amarques.smartcookbook.repository.testcontainer;

import br.com.amarques.smartcookbook.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeRepositoryTest extends TestcontainerRepositoryTestIT {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @Sql("/scripts/init_two_recipes_and_three_ingredients.sql")
    void should_return_the_recipe_that_contains_the_ingredient_that_was_sought() {
        final var recipesThatHaveRice = recipeRepository.findAllByIngredients("Rice");
        assertNotNull(recipesThatHaveRice);
        assertThat(recipesThatHaveRice.size(), is(equalTo(1)));
        assertThat(recipesThatHaveRice.get(0).getName(), is(equalTo("White Rice")));
    }

    @Test
    @Sql("/scripts/init_two_recipes_and_three_ingredients.sql")
    void should_return_the_recipes_that_contain_all_the_ingredients_that_were_searched() {
        final var recipesThatHaveRiceOrBeans = recipeRepository.findAllByIngredients("Rice|Beans");
        assertNotNull(recipesThatHaveRiceOrBeans);
        assertThat(recipesThatHaveRiceOrBeans.size(), is(equalTo(2)));
        assertThat(recipesThatHaveRiceOrBeans.get(0).getName(), is(equalTo("White Rice")));
        assertThat(recipesThatHaveRiceOrBeans.get(1).getName(), is(equalTo("Beans")));
    }
}
