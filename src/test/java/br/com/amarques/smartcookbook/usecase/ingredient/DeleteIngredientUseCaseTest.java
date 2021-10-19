package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.repository.IngredientRepository;
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
class DeleteIngredientUseCaseTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private DeleteIngredientUseCase deleteIngredientUseCase;

    @BeforeEach
    public void before() {
        this.deleteIngredientUseCase = new DeleteIngredientUseCase(ingredientRepository);
    }

    @Test
    void should_delete() {
        final var easyRandom = new EasyRandom();
        final var recipeId = easyRandom.nextLong();
        final var ingredientId = easyRandom.nextLong();

        deleteIngredientUseCase.delete(recipeId, ingredientId);

        verify(ingredientRepository).deleteByIdAndRecipeId(ingredientId, recipeId);
    }
}
