package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.dto.IngredientDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.usecase.ingredient.CreateIngredientUseCase;
import br.com.amarques.smartcookbook.usecase.ingredient.DeleteIngredientUseCase;
import br.com.amarques.smartcookbook.usecase.ingredient.GetIngredientUseCase;
import br.com.amarques.smartcookbook.usecase.ingredient.UpdateIngredientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Ingredients")
@RestController
@RequestMapping("/api/recipes/{recipeId}/ingredients")
public class IngredientResource {

    private final CreateIngredientUseCase createIngredientUseCase;
    private final UpdateIngredientUseCase updateIngredientUseCase;
    private final GetIngredientUseCase getIngredientUseCase;
    private final DeleteIngredientUseCase deleteIngredientUseCase;

    @PostMapping
    @Operation(summary = "Register a new Ingredient in a Recipe")
    public ResponseEntity<SimpleEntityDTO> create(@PathVariable final Long recipeId,
                                                  @Valid @RequestBody final CreateUpdateIngredientDTO ingredientDTO) {
        log.info("REST request to create a new Ingredient [dto: {}] in the Recipe [id: {}]", ingredientDTO, recipeId);

        final var simpleEntityDTO = createIngredientUseCase.create(recipeId, ingredientDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search for an Ingredient from a Recipe")
    public ResponseEntity<IngredientDTO> get(@PathVariable final Long recipeId, @PathVariable final Long id) {
        log.info("REST request to get an Ingredient [id: {}] from Recipe [id: {}]", id, recipeId);

        final var ingredientDTO = getIngredientUseCase.get(recipeId, id);

        return ResponseEntity.ok(ingredientDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Change the Ingredient of a Recipe")
    public ResponseEntity<Void> update(@PathVariable final Long recipeId, @PathVariable final Long id,
                                       @Valid @RequestBody final CreateUpdateIngredientDTO ingredientDTO) {
        log.info("REST request to update an Ingredient [id: {}] [dto: {}] from Recipe [id: {}]",
                id, ingredientDTO, recipeId);

        updateIngredientUseCase.update(recipeId, id, ingredientDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Search all Ingredients of a Recipe")
    public ResponseEntity<List<IngredientDTO>> gelAll(@PathVariable final Long recipeId) {
        log.info("REST request to gel all Ingredients of the Recipe [id: {}]", recipeId);

        final var ingredients = getIngredientUseCase.getAll(recipeId);

        return ResponseEntity.ok().body(ingredients);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove Ingredient from a Recipe")
    public ResponseEntity<Void> delete(@PathVariable final Long recipeId, @PathVariable final Long id) {
        log.info("REST request do delete a Ingredient [id: {}] from Recipe [id: {}]", id, recipeId);

        deleteIngredientUseCase.delete(recipeId, id);

        return ResponseEntity.ok().build();
    }
}
