package br.com.amarques.smartcookbook.usecase.recipe;

import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DeleteRecipeUseCaseTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    private DeleteRecipeUseCase deleteRecipeUseCase;

    @BeforeEach
    public void before() {
        this.deleteRecipeUseCase = new DeleteRecipeUseCase(recipeRepository, ingredientRepository);
    }

    @Test
    void should_remove_receita_and_all_ingredients() {
        final var recipeId = new EasyRandom().nextLong();

        deleteRecipeUseCase.delete(recipeId);

        verify(ingredientRepository).deleteByRecipeId(recipeId);
        verify(recipeRepository).deleteById(recipeId);
    }
}
