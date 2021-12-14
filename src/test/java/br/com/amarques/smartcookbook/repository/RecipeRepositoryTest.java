package br.com.amarques.smartcookbook.repository;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(recipesThatHaveRice).hasSize(1);
        assertThat(recipesThatHaveRice.get(0).getName()).isEqualTo("White Rice");
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

        assertThat(recipesThatHaveRiceOrBeans).hasSize(2);
        assertThat(recipesThatHaveRiceOrBeans.get(0).getName()).isEqualTo("White Rice");
        assertThat(recipesThatHaveRiceOrBeans.get(1).getName()).isEqualTo("Beans");
    }
}
