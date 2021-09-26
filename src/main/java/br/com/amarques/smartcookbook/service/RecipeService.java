package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.RecipeDTO;
import br.com.amarques.smartcookbook.dto.rest.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.exception.FindByIngredientsException;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.mapper.RecipeMapper;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public SimpleEntityDTO create(final CreateUpdateRecipeDTO receitaDTO) {
        final var recipe = RecipeMapper.toEntity(receitaDTO);
        repository.save(recipe);

        return new SimpleEntityDTO(recipe.getId());
    }

    public RecipeDTO get(final Long id) {
        final var recipe = findById(id);
        return RecipeMapper.toDTO(recipe);
    }

    protected Recipe findById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(
            "Recipe [id: {0}] not found", id)));
    }

    @Transactional
    public void update(final Long id, final CreateUpdateRecipeDTO recipeDTO) {
        final var recipe = findById(id);
        recipe.setName(recipeDTO.name);
        recipe.setWayOfDoing(recipeDTO.wayOfDoing);

        repository.save(recipe);
    }

    public List<RecipeDTO> getAll(final Pageable pageable) {
        final var recipes = repository.findAll(pageable);

        if (!recipes.hasContent()) {
            return List.of();
        }

        return recipes.stream().map(RecipeMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(final Long id) {
        ingredientRepository.deleteByRecipeId(id);
        repository.deleteById(id);
    }

    public List<RecipeDTO> findByIngredients(final List<String> ingredients) {
        if (CollectionUtils.isEmpty(ingredients)) {
            throw new FindByIngredientsException("It is necessary to inform at least one Ingredient");
        }

        final var queryParameters = buildIngredientsParameterRegex(ingredients);

        final var recipes = repository.findAllByIngredients(queryParameters);
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
