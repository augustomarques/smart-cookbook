package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.dto.IngredientDTO;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.mapper.IngredientMapper;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetIngredientUseCase {

    private final IngredientRepository ingredientRepository;

    public List<IngredientDTO> getAll(final Long receitaId) {
        final var ingredients = ingredientRepository.findAllByRecipeId(receitaId);

        if (CollectionUtils.isEmpty(ingredients)) {
            return List.of();
        }

        return ingredients.stream().map(IngredientMapper::toDTO).collect(Collectors.toList());
    }

    public IngredientDTO get(final Long recipeId, final Long id) {
        final var ingredient = getByIdAndRecipeId(id, recipeId);
        return IngredientMapper.toDTO(ingredient);
    }

    protected Ingredient getByIdAndRecipeId(final Long id, final Long recipeId) {
        return ingredientRepository.findByIdAndRecipeId(id, recipeId).orElseThrow(() -> new NotFoundException(
                String.format("Ingredient [id: %d] not found for Recipe [id: %d]", id, recipeId)));
    }
}
