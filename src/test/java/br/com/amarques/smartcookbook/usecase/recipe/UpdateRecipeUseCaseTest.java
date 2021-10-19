package br.com.amarques.smartcookbook.usecase.recipe;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UpdateRecipeUseCaseTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private GetRecipeUseCase getRecipeUseCase;

    private UpdateRecipeUseCase updateRecipeUseCase;

    private EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    public void before() {
        this.updateRecipeUseCase = new UpdateRecipeUseCase(recipeRepository, getRecipeUseCase);
    }

    @Test
    void should_update() {
        final var updateRecipeDTO = easyRandom.nextObject(CreateUpdateRecipeDTO.class);
        final var recipeId = easyRandom.nextLong();
        final var recipe = easyRandom.nextObject(Recipe.class);

        when(getRecipeUseCase.findById(recipeId)).thenReturn(recipe);

        updateRecipeUseCase.update(recipeId, updateRecipeDTO);

        recipe.setName(updateRecipeDTO.name);
        recipe.setWayOfDoing(updateRecipeDTO.wayOfDoing);

        verify(recipeRepository).save(recipe);
    }
}
