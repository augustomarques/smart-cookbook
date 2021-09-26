package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.dto.rest.IngredientDTO;
import br.com.amarques.smartcookbook.dto.rest.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.rest.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Ingredients")
@RestController
@RequestMapping("/api/recipes/{recipeId}/ingredients")
public class IngredientResource {

    private final IngredientService service;

    @PostMapping
    @Operation(summary = "Register a new Ingredient in a Recipe")
    public ResponseEntity<SimpleEntityDTO> create(@PathVariable final Long recipeId,
        @Valid @RequestBody final CreateUpdateIngredientDTO ingredientDTO) {
        log.info(String.format("REST request to create a new Ingredient [dto: %s] in the Recipe [id: %s]",
            ingredientDTO, recipeId));

        final var simpleEntityDTO = service.create(recipeId, ingredientDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search for an Ingredient from a Recipe")
    public ResponseEntity<IngredientDTO> get(@PathVariable final Long recipeId, @PathVariable final Long id) {
        log.info(String.format("REST request to get an Ingredient [id: %s] from Recipe [id: %s]", id, recipeId));

        final var ingredientDTO = service.get(recipeId, id);

        return ResponseEntity.ok(ingredientDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Change the Ingredient of a Recipe")
    public ResponseEntity<Void> update(@PathVariable final Long recipeId, @PathVariable final Long id,
        @Valid @RequestBody final CreateUpdateIngredientDTO ingredientDTO) {
        log.info(String.format("REST request to update an Ingredient [id: %s] [dto: %s] from Recipe [id: %s]",
            id, ingredientDTO, recipeId));

        service.update(recipeId, id, ingredientDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Search all Ingredients of a Recipe")
    public ResponseEntity<List<IngredientDTO>> gelAll(@PathVariable final Long recipeId) {
        log.info(String.format("REST request to gel all Ingredients of the Recipe [id: %s]", recipeId));

        final var ingredients = service.getAll(recipeId);

        return ResponseEntity.ok().body(ingredients);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove Ingredient from a Recipe")
    public ResponseEntity<Void> delete(@PathVariable final Long recipeId, @PathVariable final Long id) {
        log.info(String.format("REST request do delete a Ingredient [id: %s] from Recipe [id: %s]", id, recipeId));

        service.delete(recipeId, id);

        return ResponseEntity.ok().build();
    }

}
