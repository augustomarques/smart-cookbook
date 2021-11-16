package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.dto.rest.RecipeDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateRecipeDTO;
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

    public static Recipe toEntity(final CreateRecipeMessageDTO createRecipeMessageDTO) {
        var recipe = new Recipe();
        recipe.setName(createRecipeMessageDTO.name);
        recipe.setWayOfDoing(createRecipeMessageDTO.wayOfDoing);
        return recipe;
    }

    public static RecipeDTO toDTO(final Recipe recipe) {
        return new RecipeDTO(recipe.getId(), recipe.getName(), recipe.getWayOfDoing());
    }

}
