package br.com.amarques.smartcookbook.usecase.recipe;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.RecipeDTO;
import br.com.amarques.smartcookbook.exception.FindByIngredientsException;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.mapper.RecipeMapper;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetRecipeUseCase {

    private final RecipeRepository recipeRepository;

    public RecipeDTO get(final Long id) {
        final var recipe = findById(id);
        return RecipeMapper.toDTO(recipe);
    }

    public Recipe findById(final Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Recipe [id: %d] not found", id)));
    }

    public List<RecipeDTO> getAll(final Pageable pageable) {
        final var recipes = recipeRepository.findAll(pageable);

        if (!recipes.hasContent()) {
            return List.of();
        }

        return recipes.stream().map(RecipeMapper::toDTO).collect(Collectors.toList());
    }

    public List<RecipeDTO> findByIngredients(final List<String> ingredients) {
        if (CollectionUtils.isEmpty(ingredients)) {
            throw new FindByIngredientsException("It is necessary to inform at least one Ingredient");
        }

        final var queryParameters = buildIngredientsParameterRegex(ingredients);

        final var recipes = recipeRepository.findAllByIngredients(queryParameters);
        if (CollectionUtils.isEmpty(recipes)) {
            return List.of();
        }

        return recipes.stream().map(RecipeMapper::toDTO).collect(Collectors.toList());
    }

    private static String buildIngredientsParameterRegex(final List<String> ingredients) {
        final var query = new StringBuilder();

        for (int i = 0; i < ingredients.size(); i++) {
            final var ingredient = ingredients.get(i);
            query.append(ingredient);

            if (i < (ingredients.size() - 1)) {
                query.append("|");
            }
        }

        return query.toString();
    }

}
