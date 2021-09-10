package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.RecipeDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateRecipeDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeMapper {

    public static Recipe toEntity(final CreateUpdateRecipeDTO dto) {
        var recipe = new Recipe();
        recipe.setName(dto.name);
        recipe.setWayOfDoing(dto.wayOfDoing);
        return recipe;
    }

    public static RecipeDTO toDTO(final Recipe recipe) {
        return new RecipeDTO(recipe.getId(), recipe.getName(), recipe.getWayOfDoing());
    }
}
