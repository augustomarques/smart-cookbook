package br.com.amarques.smartcookbook.usecase.recipe;

import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.dto.rest.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.mapper.RecipeMapper;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import br.com.amarques.smartcookbook.usecase.ingredient.CreateIngredientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateRecipeUseCase {

    private final RecipeRepository recipeRepository;
    private final CreateIngredientUseCase createIngredientUseCase;

    @Transactional
    public SimpleEntityDTO create(final CreateUpdateRecipeDTO receitaDTO) {
        final var recipe = RecipeMapper.toEntity(receitaDTO);
        recipeRepository.save(recipe);

        return new SimpleEntityDTO(recipe.getId());
    }

    @Transactional
    public void create(final CreateRecipeMessageDTO createRecipeMessageDTO) {
        final var recipe = RecipeMapper.toEntity(createRecipeMessageDTO);
        recipeRepository.save(recipe);

        createRecipeMessageDTO.ingredients.forEach(ingredient -> createIngredientUseCase.create(recipe, ingredient));
    }
}
