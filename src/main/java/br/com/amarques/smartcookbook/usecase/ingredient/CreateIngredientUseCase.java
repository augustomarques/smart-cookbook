package br.com.amarques.smartcookbook.usecase.ingredient;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.mapper.IngredientMapper;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.usecase.recipe.GetRecipeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateIngredientUseCase {

    private final IngredientRepository ingredientRepository;
    private final GetRecipeUseCase getRecipeUseCase;

    @Transactional
    public SimpleEntityDTO create(final Long receitaId, final CreateUpdateIngredientDTO ingredientDTO) {
        final var recipe = getRecipeUseCase.findById(receitaId);

        return create(recipe, ingredientDTO.name);
    }

    @Transactional
    public SimpleEntityDTO create(final Recipe recipe, final String name) {
        final var ingredient = IngredientMapper.toEntity(name, recipe);

        ingredientRepository.save(ingredient);

        return new SimpleEntityDTO(ingredient.getId());
    }
}
