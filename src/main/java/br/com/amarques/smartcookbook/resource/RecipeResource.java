package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.dto.RecipeDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Recipes")
@RestController
@RequestMapping("/api/recipes")
public class RecipeResource {

    private final RecipeService service;

    @PostMapping
    @Operation(summary = "Register a new recipe")
    public ResponseEntity<SimpleEntityDTO> create(@Valid @RequestBody final CreateUpdateRecipeDTO recipeDTO) {
        log.info(String.format("REST request to create a new Recipe %s", recipeDTO));

        final var simpleEntityDTO = service.create(recipeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search a Recipe by ID")
    public ResponseEntity<RecipeDTO> get(@PathVariable final Long id) {
        log.info(String.format("REST request to get an Recipe [id: %s]", id));

        final var recipeDTO = service.get(id);

        return ResponseEntity.ok(recipeDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Change a registered Recipe")
    public ResponseEntity<Void> update(@PathVariable final Long id,
                                       @Valid @RequestBody final CreateUpdateRecipeDTO receitaDTO) {
        log.info(String.format("REST request to update an Recipe [id: %s] [dto: %s]", id, receitaDTO));

        service.update(id, receitaDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Search all recipes")
    public ResponseEntity<List<RecipeDTO>> gelAll(@RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                                  @RequestParam(value = "size", defaultValue = "10", required = false) final int size) {
        log.info(String.format("REST request to gel all Recipes [Page: %d  - Size: %d]", page, size));

        final var recipes = service.getAll(PageRequest.of(page, size));

        return ResponseEntity.ok().body(recipes);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a Recipe")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        log.info(String.format("REST request to delete an Recipe [id: %s] and all Ingredients", id));

        service.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search for recipes that have one of the ingredients listed")
    public ResponseEntity<List<RecipeDTO>> findByIngredientes(@RequestParam final List<String> ingredients) {
        log.info(String.format("REST request to get Recipes from Ingredients [%s]", ingredients));

        final var recipes = service.findByIngredients(ingredients);

        return ResponseEntity.ok().body(recipes);
    }
}
