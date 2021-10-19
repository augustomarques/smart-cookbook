package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.usecase.recipe.GetRecipeUseCase;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreateIngredientUseCaseTest {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private GetRecipeUseCase getRecipeUseCase;

    private CreateIngredientUseCase createIngredientUseCase;

    @BeforeEach
    public void before() {
        this.createIngredientUseCase = new CreateIngredientUseCase(ingredientRepository, getRecipeUseCase);
    }

    @Test
    void should_create_new_ingrediente() {
        final var easyRandom = new EasyRandom();
        final var recipeId = easyRandom.nextLong();

        when(getRecipeUseCase.findById(recipeId)).thenReturn(new Recipe());

        final var ingredientDTO = easyRandom.nextObject(CreateUpdateIngredientDTO.class);

        final var simpleEntityDTO = createIngredientUseCase.create(recipeId, ingredientDTO);

        assertNotNull(simpleEntityDTO);
    }
}
