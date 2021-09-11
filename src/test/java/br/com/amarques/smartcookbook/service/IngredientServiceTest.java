package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeService recipeService;

    private IngredientService ingredientService;

    @BeforeEach
    public void before() {
        this.ingredientService = new IngredientService(ingredientRepository, recipeService);
    }

    @Test
    void should_return_registered_ingredient() {
        when(ingredientRepository.findByIdAndRecipeId(INGREDIENT_ID, RECIPE_ID))
            .thenReturn(Optional.of(buildIngredient()));

        final var ingredientDTO = ingredientService.get(RECIPE_ID, INGREDIENT_ID);

        assertNotNull(ingredientDTO);
        assertThat(ingredientDTO.id, is(equalTo(INGREDIENT_ID)));
        assertThat(ingredientDTO.name, is(equalTo(NAME)));
    }

    @Test
    void should_throw_not_found_exception_when_find_unregistered_id() {
        when(ingredientRepository.findByIdAndRecipeId(INGREDIENT_ID, RECIPE_ID)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> ingredientService.get(RECIPE_ID,
            INGREDIENT_ID));

        assertEquals(exception.getMessage(), MessageFormat.format(
            "Ingredient [id: {0}] not found for Recipe [id: {1}]",
            INGREDIENT_ID, RECIPE_ID));
    }

    @Test
    void should_return_all_ingredients_of_recipe() {
        when(ingredientRepository.findAllByRecipeId(RECIPE_ID))
            .thenReturn(List.of(buildIngredient(), buildIngredient()));

        final var ingredients = ingredientService.getAll(RECIPE_ID);

        assertNotNull(ingredients);
        assertThat(ingredients.size(), is(equalTo(2)));
    }

    @Test
    void should_find_all_and_return_none() {
        when(ingredientRepository.findAllByRecipeId(RECIPE_ID)).thenReturn(List.of());

        final var ingredients = ingredientService.getAll(RECIPE_ID);

        assertTrue(ingredients.isEmpty());
    }

    @Test
    void should_create_new_ingredient() {
        when(recipeService.findById(RECIPE_ID)).thenReturn(new Recipe());

        final var ingredienteDTO = new CreateUpdateIngredientDTO(NAME);

        final var simpleEntityDTO = ingredientService.create(RECIPE_ID, ingredienteDTO);

        assertNotNull(simpleEntityDTO);
    }

    @Test
    void should_update_ingredient() {
        when(ingredientRepository.findByIdAndRecipeId(INGREDIENT_ID, RECIPE_ID))
            .thenReturn(Optional.of(buildIngredient()));

        final var ingredientDTO = new CreateUpdateIngredientDTO("new name");

        final var ingredient = buildIngredient();
        ingredient.setName(ingredientDTO.name);

        ingredientService.update(RECIPE_ID, INGREDIENT_ID, ingredientDTO);

        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void should_delete() {
        ingredientService.delete(RECIPE_ID, INGREDIENT_ID);

        verify(ingredientRepository, times(1)).deleteByIdAndRecipeId(INGREDIENT_ID, RECIPE_ID);
    }

    public static Ingredient buildIngredient() {
        final var ingredient = new Ingredient(RECIPE);
        ingredient.setId(INGREDIENT_ID);
        ingredient.setName(NAME);
        return ingredient;
    }

    private static final Long RECIPE_ID = 999L;
    private static final Long INGREDIENT_ID = 123L;
    private static final String NAME = "Rice";
    private static final Recipe RECIPE = new Recipe();

}
