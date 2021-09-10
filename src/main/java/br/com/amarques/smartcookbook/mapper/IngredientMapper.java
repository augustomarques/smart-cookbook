package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.IngredientDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientMapper {

    public static Ingredient toEntity(final CreateUpdateIngredientDTO dto, final Recipe recipe) {
        final var ingredient = new Ingredient(recipe);
        ingredient.setName(dto.name);
        return ingredient;
    }

    public static IngredientDTO toDTO(final Ingredient ingredient) {
        return new IngredientDTO(ingredient.getId(), ingredient.getName());
    }
}