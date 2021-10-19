package br.com.amarques.smartcookbook.usecase.recipe;

import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateRecipeUseCase {

    private final RecipeRepository recipeRepository;
    private final GetRecipeUseCase getRecipeUseCase;

    @Transactional
    public void update(final Long id, final CreateUpdateRecipeDTO recipeDTO) {
        final var recipe = getRecipeUseCase.findById(id);
        recipe.setName(recipeDTO.name);
        recipe.setWayOfDoing(recipeDTO.wayOfDoing);

        recipeRepository.save(recipe);
    }

}
