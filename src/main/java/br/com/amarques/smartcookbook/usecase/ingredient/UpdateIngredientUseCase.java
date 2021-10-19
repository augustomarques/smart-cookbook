package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpdateIngredientUseCase {

    private final IngredientRepository ingredientRepository;
    private final GetIngredientUseCase getIngredientUseCase;

    @Transactional
    public void update(final Long recipeId, final Long id, final CreateUpdateIngredientDTO ingredientDTO) {
        final var ingredient = getIngredientUseCase.getByIdAndRecipeId(id, recipeId);
        ingredient.setName(ingredientDTO.name);
        ingredientRepository.save(ingredient);
    }

}
