package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.RecipeDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.exception.FindByIngredientsException;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    private RecipeService recipeService;

    @BeforeEach
    public void before() {
        this.recipeService = new RecipeService(recipeRepository, ingredientRepository);
    }

    @Test
    void should_find_by_id_and_return_dto() {
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(buildRecipe()));

        RecipeDTO recipeDTO = recipeService.get(ID);

        assertNotNull(recipeDTO);
        assertThat(recipeDTO.id, is(equalTo(ID)));
        assertThat(recipeDTO.name, is(equalTo(NAME)));
        assertThat(recipeDTO.wayOfDoing, is(equalTo(WAY_OF_DOING)));
    }

    @Test
    void should_throw_not_found_exception_when_find_by_id_not_registered() {
        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> recipeService.get(ID));

        assertEquals(exception.getMessage(), MessageFormat.format("Recipe [id: {0}] not found", ID));
    }

    @Test
    void should_find_all_and_return_none() {
        final var pageable = PageRequest.of(0, 10);
        final List<Recipe> recipes = List.of();
        final var pageOfRecipe = new PageImpl<>(recipes.subList(0, 0), pageable, recipes.size());

        when(recipeRepository.findAll(pageable)).thenReturn(pageOfRecipe);

        final var recipesDTO = recipeService.getAll(pageable);

        assertTrue(recipesDTO.isEmpty());
    }

    @Test
    void should_find_all_and_return_two_receitas() {
        final var pageable = PageRequest.of(0, 10);
        final var recipes = List.of(buildRecipe(), buildRecipe());
        final var pageOfReceita = new PageImpl<>(recipes.subList(0, 2), pageable, recipes.size());

        when(recipeRepository.findAll(pageable)).thenReturn(pageOfReceita);

        final var recipesDTO = recipeService.getAll(pageable);

        assertFalse(recipesDTO.isEmpty());
        assertThat(recipesDTO.size(), is(equalTo(2)));
    }

    @Test
    void should_create_a_new_empresa() {
        final var createUpdateRecipeDTO = new CreateUpdateRecipeDTO(NAME, WAY_OF_DOING);
        final var recipe = buildRecipe();
        recipe.setId(null);

        recipeService.create(createUpdateRecipeDTO);

        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void should_update() {
        final var createUpdateRecipeDTO = new CreateUpdateRecipeDTO("new name", "new way to cook white rice");

        when(recipeRepository.findById(ID)).thenReturn(Optional.of(buildRecipe()));

        recipeService.update(ID, createUpdateRecipeDTO);

        final var recipe = buildRecipe();
        recipe.setName("new name");
        recipe.setWayOfDoing("new way to cook white rice");

        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void should_remove_receita_and_all_ingredients() {
        recipeService.delete(ID);

        verify(ingredientRepository, times(1)).deleteByRecipeId(ID);
        verify(recipeRepository, times(1)).deleteById(ID);
    }

    @Test
    void should_throw_an_exception_when_no_ingredient_is_reported() {
        Exception exception = assertThrows(FindByIngredientsException.class, () -> recipeService.findByIngredients(
            List.of()));

        assertEquals("It is necessary to inform at least one Ingredient", exception.getMessage());
    }

    @Test
    void should_generate_the_search_parameters_correctly() {
        final var ingredients = List.of("Ingrediant 1", "Ingrediant 2");
        final var queryParameter = "Ingrediant 1|Ingrediant 2";

        recipeService.findByIngredients(ingredients);

        verify(recipeRepository, times(1)).findAllByIngredients(queryParameter);
    }

    public static Recipe buildRecipe() {
        final var recipe = new Recipe();
        recipe.setId(ID);
        recipe.setName(NAME);
        recipe.setWayOfDoing(WAY_OF_DOING);
        return recipe;
    }

    private static final Long ID = 123L;
    private static final String NAME = "White rice";
    private static final String WAY_OF_DOING = "way to cook white rice";

}
