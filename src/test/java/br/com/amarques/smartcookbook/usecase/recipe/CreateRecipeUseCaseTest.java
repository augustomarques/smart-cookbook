package br.com.amarques.smartcookbook.usecase.recipe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import br.com.amarques.smartcookbook.usecase.ingredient.CreateIngredientUseCase;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreateRecipeUseCaseTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private CreateIngredientUseCase createIngredientUseCase;

    private CreateRecipeUseCase createRecipeUseCase;

    @BeforeEach
    public void before() {
        this.createRecipeUseCase = new CreateRecipeUseCase(recipeRepository, createIngredientUseCase);
    }

    @Test
    void should_create_a_new_recipe() {
        final var createRecipeDTO = new EasyRandom().nextObject(CreateUpdateRecipeDTO.class);
        final var recipe = new Recipe();
        recipe.setName(createRecipeDTO.name);
        recipe.setWayOfDoing(createRecipeDTO.wayOfDoing);

        createRecipeUseCase.create(createRecipeDTO);

        verify(recipeRepository).save(recipe);
    }

    @Test
    void should_create_a_new_recipe_with_ingredients() {
        final var easyRandom = new EasyRandom();
        final var recipeName = easyRandom.nextObject(String.class);
        final var createRecipeMessageDTO = new CreateRecipeMessageDTO(easyRandom.nextObject(String.class),
                easyRandom.objects(String.class, 3).collect(Collectors.toList()));

        final var recipe = new Recipe();
        recipe.setName(recipeName);
        recipe.setWayOfDoing(createRecipeMessageDTO.wayOfDoing);

        createRecipeUseCase.create(recipeName, createRecipeMessageDTO);

        verify(recipeRepository).save(recipe);
        verify(createIngredientUseCase, times(3)).create(any(Recipe.class), anyString());
    }

}
