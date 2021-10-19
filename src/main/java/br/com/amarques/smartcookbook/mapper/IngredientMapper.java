package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.rest.IngredientDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateIngredientDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientMapper {

    public static Ingredient toEntity(final CreateUpdateIngredientDTO dto, final Recipe recipe) {
        return toEntity(dto.name, recipe);
    }

    public static Ingredient toEntity(final String name, final Recipe recipe) {
        final var ingredient = new Ingredient(recipe);
        ingredient.setName(name);
        return ingredient;
    }

    public static Ingredient toEntity(final String name, final Recipe recipe) {
        final var ingredient = new Ingredient(recipe);
        ingredient.setName(name);
        return ingredient;
    }

    public static IngredientDTO toDTO(final Ingredient ingredient) {
        return new IngredientDTO(ingredient.getId(), ingredient.getName());
    }

}
