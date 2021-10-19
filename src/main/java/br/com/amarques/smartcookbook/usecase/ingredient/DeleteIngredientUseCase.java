package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeleteIngredientUseCase {

    private final IngredientRepository ingredientRepository;

    @Transactional
    public void delete(final Long recipeId, final Long id) {
        ingredientRepository.deleteByIdAndRecipeId(id, recipeId);
    }

}
