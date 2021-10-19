package br.com.amarques.smartcookbook.usecase.recipe;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.RecipeDTO;
import br.com.amarques.smartcookbook.exception.FindByIngredientsException;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GetRecipeUseCaseTest {

    @Mock
    private RecipeRepository recipeRepository;

    private GetRecipeUseCase getRecipeUseCase;

    private EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    public void before() {
        this.getRecipeUseCase = new GetRecipeUseCase(recipeRepository);
    }

    @Test
    void should_find_by_id_and_return_dto() {
        final var recipe = easyRandom.nextObject(Recipe.class);
        final var recipeId = recipe.getId();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        final var recipeDTO = getRecipeUseCase.get(recipeId);

        assertThat(recipeDTO.id).isEqualTo(recipe.getId());
        assertThat(recipeDTO.name).isEqualTo(recipe.getName());
        assertThat(recipeDTO.wayOfDoing).isEqualTo(recipe.getWayOfDoing());
    }

    @Test
    void should_throw_not_found_exception_when_find_by_id_not_registered() {
        final var recipeId = easyRandom.nextLong();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> getRecipeUseCase.get(recipeId));

        assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessage(String.format("Recipe [id: %d] not found", recipeId));
    }

    @Test
    void should_find_all_and_return_none() {
        final var pageable = PageRequest.of(0, 10);
        final List<Recipe> recipes = List.of();
        final Page<Recipe> pageOfReceita = new PageImpl<>(recipes.subList(0, 0), pageable, recipes.size());

        when(recipeRepository.findAll(pageable)).thenReturn(pageOfReceita);

        final List<RecipeDTO> receitasDTO = getRecipeUseCase.getAll(pageable);

        assertTrue(receitasDTO.isEmpty());
    }

    @Test
    void should_find_all_and_return_two_receitas() {
        final var pageable = PageRequest.of(0, 10);
        final List<Recipe> recipes = easyRandom.objects(Recipe.class, 2).collect(Collectors.toList());
        final Page<Recipe> pageOfReceita = new PageImpl<>(recipes.subList(0, 2), pageable, recipes.size());

        when(recipeRepository.findAll(pageable)).thenReturn(pageOfReceita);

        final List<RecipeDTO> receitasDTO = getRecipeUseCase.getAll(pageable);

        assertThat(receitasDTO).hasSize(2);
    }

    @Test
    void should_throw_an_exception_when_no_ingredient_is_reported() {
        final Throwable throwable = catchThrowable(() -> getRecipeUseCase.findByIngredients(List.of()));

        assertThat(throwable)
                .isInstanceOf(FindByIngredientsException.class)
                .hasMessage(String.format("It is necessary to inform at least one Ingredient"));
    }

    @Test
    void should_generate_the_search_parameters_correctly() {
        final List<String> ingredients = List.of("Ingrediante 1", "Ingrediante 2");
        final var queryParameter = "Ingrediante 1|Ingrediante 2";

        getRecipeUseCase.findByIngredients(ingredients);

        verify(recipeRepository).findAllByIngredients(queryParameter);
    }
}
