package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateIngredientDTO;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UpdateIngredientUseCaseTest {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private GetIngredientUseCase getIngredientUseCase;

    private UpdateIngredientUseCase updateIngredientUseCase;

    @BeforeEach
    public void before() {
        this.updateIngredientUseCase = new UpdateIngredientUseCase(ingredientRepository, getIngredientUseCase);
    }

    @Test
    void should_update_ingredient() {
        final var easyRandom = new EasyRandom();
        final var recipeId = easyRandom.nextLong();
        final var ingredientId = easyRandom.nextLong();
        final var ingredient = easyRandom.nextObject(Ingredient.class);
        final var ingredientDTO = easyRandom.nextObject(CreateUpdateIngredientDTO.class);

        when(getIngredientUseCase.getByIdAndRecipeId(ingredientId, recipeId)).thenReturn(ingredient);

        ingredient.setName(ingredientDTO.name);

        updateIngredientUseCase.update(recipeId, ingredientId, ingredientDTO);

        verify(ingredientRepository).save(ingredient);
    }
}
