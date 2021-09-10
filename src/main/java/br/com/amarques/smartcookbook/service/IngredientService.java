package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.dto.IngredientDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.mapper.IngredientMapper;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository repository;
    private final RecipeService recipeService;

    @Transactional
    public SimpleEntityDTO create(final Long receitaId, final CreateUpdateIngredientDTO ingredientDTO) {
        final var recipe = recipeService.findById(receitaId);
        final var ingredient = IngredientMapper.toEntity(ingredientDTO, recipe);

        repository.save(ingredient);

        return new SimpleEntityDTO(ingredient.getId());
    }

    public IngredientDTO get(final Long recipeId, final Long id) {
        final var ingredient = getByIdAndRecipeId(id, recipeId);
        return IngredientMapper.toDTO(ingredient);
    }

    @Transactional
    public void update(final Long recipeId, final Long id, final CreateUpdateIngredientDTO ingredientDTO) {
        final var ingredient = getByIdAndRecipeId(id, recipeId);
        ingredient.setName(ingredientDTO.name);
        repository.save(ingredient);
    }

    private Ingredient getByIdAndRecipeId(final Long id, final Long recipeId) {
        return repository.findByIdAndRecipeId(id, recipeId).orElseThrow(() -> new NotFoundException(MessageFormat.format(
                "Ingredient [id: {0}] not found for Recipe [id: {1}]", id, recipeId)));
    }

    public List<IngredientDTO> getAll(final Long receitaId) {
        final var ingredients = repository.findAllByRecipeId(receitaId);

        if (CollectionUtils.isEmpty(ingredients)) {
            return List.of();
        }

        return ingredients.stream().map(IngredientMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(final Long recipeId, final Long id) {
        repository.deleteByIdAndRecipeId(id, recipeId);
    }
}
