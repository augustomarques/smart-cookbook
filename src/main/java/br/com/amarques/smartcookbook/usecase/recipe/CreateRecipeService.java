package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.dto.rest.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.mapper.IngredientMapper;
import br.com.amarques.smartcookbook.mapper.RecipeMapper;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateRecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public void create(final String name, final CreateRecipeMessageDTO recipeMessageDTO) {
        final var recipe = RecipeMapper.toEntity(name, recipeMessageDTO.wayOfDoing);
        recipeRepository.save(recipe);

        final var ingredients = recipeMessageDTO.ingredients
            .stream()
            .map(ingredient -> IngredientMapper.toEntity(ingredient, recipe))
            .collect(Collectors.toList());

        ingredientRepository.saveAll(ingredients);
    }

    @Transactional
    public SimpleEntityDTO create(final CreateUpdateRecipeDTO receitaDTO) {
        final var recipe = RecipeMapper.toEntity(receitaDTO);
        recipeRepository.save(recipe);

        return new SimpleEntityDTO(recipe.getId());
    }

}
