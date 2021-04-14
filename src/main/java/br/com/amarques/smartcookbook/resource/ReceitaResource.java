package br.com.amarques.smartcookbook.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;
import br.com.amarques.smartcookbook.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Receitas")
@RestController
@RequestMapping("/receitas")
public class ReceitaResource {

    private final ReceitaService service;

    @PostMapping
    @Operation(summary = "Cadastra uma nova Receita")
    public ResponseEntity<SimpleEntityDTO> create(@Valid @RequestBody CreateUpdateReceitaDTO receitaDTO) {
        log.info(String.format("REST request to create a new Receita %s", receitaDTO));

        SimpleEntityDTO simpleEntityDTO = service.create(receitaDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma Receita pelo ID")
    public ResponseEntity<ReceitaDTO> get(@PathVariable Long id) {
        log.info(String.format("REST request to get an Receita [id: %s]", id));

        ReceitaDTO receitaDTO = service.get(id);

        return ResponseEntity.ok(receitaDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera uma Receita cadastrada")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CreateUpdateReceitaDTO receitaDTO) {
        log.info(String.format("REST request to update an Receita [id: %s] [dto: %s]", id, receitaDTO));

        service.update(id, receitaDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Busca todas das receitas")
    public ResponseEntity<List<ReceitaDTO>> gelAll(@RequestParam(value = "page", defaultValue = "0",
            required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        log.info(String.format("REST request to gel all Receitas [Page: %d  - Size: %d]", page, size));

        List<ReceitaDTO> receitas = service.getAll(PageRequest.of(page, size));

        return ResponseEntity.ok().body(receitas);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma Receita")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info(String.format("REST request to delete an Receita [id: %s] and all Ingredientes", id));

        service.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca Receitas que tenham um dos ingredientes informados")
    public ResponseEntity<List<ReceitaDTO>> findByIngredientes(@RequestParam List<String> ingredientes) {
        log.info(String.format("REST request to get Receitas from Ingredientes [%s]", ingredientes));

        List<ReceitaDTO> receitas = service.findByIngredientes(ingredientes);

        return ResponseEntity.ok().body(receitas);
    }
}
