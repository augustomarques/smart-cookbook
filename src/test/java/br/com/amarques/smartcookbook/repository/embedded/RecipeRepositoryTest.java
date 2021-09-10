package br.com.amarques.smartcookbook.repository.embedded;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeRepositoryTest extends EmbeddedRepositoryTestIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void should_return_the_recipe_that_contains_the_ingredient_that_was_sought() {
        final var whiteRiceRecipe = new Recipe();
        whiteRiceRecipe.setName("White Rice");
        whiteRiceRecipe.setWayOfDoing("way to cook white rice");

        final var beansRecipe = new Recipe();
        beansRecipe.setName("Beans");
        beansRecipe.setWayOfDoing("way to cook beans");

        entityManager.persist(whiteRiceRecipe);
        entityManager.persist(beansRecipe);

        final var riceIngredient = new Ingredient(whiteRiceRecipe);
        riceIngredient.setName("Rice");
        final var garlicIngredient = new Ingredient(whiteRiceRecipe);
        garlicIngredient.setName("Garlic");
        final var beanIngredient = new Ingredient(beansRecipe);
        beanIngredient.setName("Beans");

        entityManager.persist(riceIngredient);
        entityManager.persist(garlicIngredient);
        entityManager.persist(beanIngredient);

        final var recipesThatHaveRice = recipeRepository.findAllByIngredients("Rice");
        assertNotNull(recipesThatHaveRice);
        assertThat(recipesThatHaveRice.size(), is(equalTo(1)));
        assertThat(recipesThatHaveRice.get(0).getName(), is(equalTo("White Rice")));
    }

    @Test
    void should_return_the_recipes_that_contain_all_the_ingredients_that_were_searched() {
        final var whiteRiceRecipe = new Recipe();
        whiteRiceRecipe.setName("White Rice");
        whiteRiceRecipe.setWayOfDoing("way to cook white rice");

        final var beansRecipe = new Recipe();
        beansRecipe.setName("Beans");
        beansRecipe.setWayOfDoing("way to cook beans");

        entityManager.persist(whiteRiceRecipe);
        entityManager.persist(beansRecipe);

        final var riceIngredient = new Ingredient(whiteRiceRecipe);
        riceIngredient.setName("Rice");
        final var garlicIngredient = new Ingredient(whiteRiceRecipe);
        garlicIngredient.setName("Garlic");
        final var beanIngredient = new Ingredient(beansRecipe);
        beanIngredient.setName("Beans");

        entityManager.persist(riceIngredient);
        entityManager.persist(garlicIngredient);
        entityManager.persist(beanIngredient);

        final var recipesThatHaveRiceOrBeans = recipeRepository.findAllByIngredients("Rice|Beans");
        assertNotNull(recipesThatHaveRiceOrBeans);
        assertThat(recipesThatHaveRiceOrBeans.size(), is(equalTo(2)));
        assertThat(recipesThatHaveRiceOrBeans.get(0).getName(), is(equalTo("White Rice")));
        assertThat(recipesThatHaveRiceOrBeans.get(1).getName(), is(equalTo("Beans")));
    }
}
