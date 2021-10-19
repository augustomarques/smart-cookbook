package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GetIngredientUseCaseTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private GetIngredientUseCase getIngredientUseCase;

    private EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    public void before() {
        this.getIngredientUseCase = new GetIngredientUseCase(ingredientRepository);
    }

    @Test
    void should_return_registered_ingredient() {
        final var recipeId = easyRandom.nextLong();
        final var ingredientId = easyRandom.nextLong();
        final var ingredient = easyRandom.nextObject(Ingredient.class);

        when(ingredientRepository.findByIdAndRecipeId(ingredientId, recipeId)).thenReturn(Optional.of(ingredient));

        final var ingredientDTO = getIngredientUseCase.get(recipeId, ingredientId);

        assertNotNull(ingredientDTO);
        assertThat(ingredientDTO.id).isEqualTo(ingredient.getId());
        assertThat(ingredientDTO.name).isEqualTo(ingredient.getName());
    }

    @Test
    void should_throw_not_found_exception_when_find_unregistered_id() {
        final var recipeId = easyRandom.nextLong();
        final var ingredientId = easyRandom.nextLong();

        when(ingredientRepository.findByIdAndRecipeId(ingredientId, recipeId)).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> getIngredientUseCase.get(recipeId, ingredientId));

        assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessage(String.format("Ingredient [id: %d] not found for Recipe [id: %d]", ingredientId, recipeId));
    }

    @Test
    void should_return_all_ingredients_of_recipe() {
        final var recipeId = easyRandom.nextLong();
        final var ingredients = easyRandom.objects(Ingredient.class, 2).collect(Collectors.toList());

        when(ingredientRepository.findAllByRecipeId(recipeId)).thenReturn(ingredients);

        final var ingredientsDTO = getIngredientUseCase.getAll(recipeId);

        assertThat(ingredientsDTO).hasSize(2);
    }

    @Test
    void should_find_all_and_return_none() {
        final var recipeId = easyRandom.nextLong();

        when(ingredientRepository.findAllByRecipeId(recipeId)).thenReturn(List.of());

        final var ingredients = getIngredientUseCase.getAll(recipeId);

        assertThat(ingredients).isEmpty();
    }
}
