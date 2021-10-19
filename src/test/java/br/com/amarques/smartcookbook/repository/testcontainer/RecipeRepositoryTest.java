package br.com.amarques.smartcookbook.repository.testcontainer;

import br.com.amarques.smartcookbook.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeRepositoryTest extends TestcontainerRepositoryTestIT {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @Sql("/scripts/init_two_recipes_and_three_ingredients.sql")
    void should_return_the_recipe_that_contains_the_ingredient_that_was_sought() {
        final var recipesThatHaveRice = recipeRepository.findAllByIngredients("Rice");

        assertThat(recipesThatHaveRice).hasSize(1);
        assertThat(recipesThatHaveRice.get(0).getName()).isEqualTo("White Rice");
    }

    @Test
    @Sql("/scripts/init_two_recipes_and_three_ingredients.sql")
    void should_return_the_recipes_that_contain_all_the_ingredients_that_were_searched() {
        final var recipesThatHaveRiceOrBeans = recipeRepository.findAllByIngredients("Rice|Beans");

        assertThat(recipesThatHaveRiceOrBeans).hasSize(2);
        assertThat(recipesThatHaveRiceOrBeans.get(0).getName()).isEqualTo("White Rice");
        assertThat(recipesThatHaveRiceOrBeans.get(1).getName()).isEqualTo("Beans");
    }
}
