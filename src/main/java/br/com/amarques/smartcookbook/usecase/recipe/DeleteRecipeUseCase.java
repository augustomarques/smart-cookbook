package br.com.amarques.smartcookbook.usecase.recipe;

import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteRecipeUseCase {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public void delete(final Long id) {
        ingredientRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }

}
