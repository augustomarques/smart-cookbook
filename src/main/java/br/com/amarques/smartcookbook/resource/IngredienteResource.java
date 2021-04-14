package br.com.amarques.smartcookbook.resource;

import java.util.List;

import javax.validation.Valid;

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

import br.com.amarques.smartcookbook.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredienteDTO;
import br.com.amarques.smartcookbook.service.IngredienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Api(value = "Ingredientes", tags = {"Ingredientes Resource"})
@RestController
@RequestMapping("/receitas/{receitaId}/ingredientes")
public class IngredienteResource {

    private final IngredienteService service;

    @PostMapping
    @ApiOperation(value = "Cadastra um novo Ingrediente em uma Receita")
    public ResponseEntity<SimpleEntityDTO> create(@PathVariable Long receitaId,
            @Valid @RequestBody CreateUpdateIngredienteDTO ingredienteDTO) {
        log.info(String.format("REST request to create a new Ingrediente [dto: %s] in the Receita [id: %s]",
                ingredienteDTO, receitaId));

        SimpleEntityDTO simpleEntityDTO = service.create(receitaId, ingredienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca um Ingrediente de uma Receita")
    public ResponseEntity<IngredienteDTO> get(@PathVariable Long receitaId, @PathVariable Long id) {
        log.info(String.format("REST request to get an Ingrediente [id: %s] from Receita [id: %s]", id, receitaId));

        IngredienteDTO ingredienteDTO = service.get(receitaId, id);

        return ResponseEntity.ok(ingredienteDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Altera o Ingrediente de uma Receita")
    public ResponseEntity<Void> update(@PathVariable Long receitaId, @PathVariable Long id,
            @Valid @RequestBody CreateUpdateIngredienteDTO ingredienteDTO) {
        log.info(String.format("REST request to update an Ingrediente [id: %s] [dto: %s] from Receita [id: %s]", id,
                ingredienteDTO, receitaId));

        service.update(receitaId, id, ingredienteDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiOperation(value = "Busca todos os Ingredientes de uma Receita")
    public ResponseEntity<List<IngredienteDTO>> gelAll(@PathVariable Long receitaId) {
        log.info(String.format("REST request to gel all Ingredientes of the Receita [id: %s]", receitaId));

        List<IngredienteDTO> ingredientes = service.getAll(receitaId);

        return ResponseEntity.ok().body(ingredientes);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o Ingrediente de uma Receita")
    public ResponseEntity<Void> delete(@PathVariable Long receitaId, @PathVariable Long id) {
        log.info(String.format("REST request do delete a Ingrediente [id: %s] from Receita [id: %s]", id, receitaId));

        service.delete(receitaId, id);

        return ResponseEntity.ok().build();
    }
}
