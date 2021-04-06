package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredienteDTO;
import br.com.amarques.smartcookbook.service.IngredienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Api(value = "Ingredientes", tags = {"Ingredientes Resource"})
@RestController
@RequestMapping("/receitas/{receitaId}/ingredientes")
public class IngredienteResource {

    private final Logger logger = LoggerFactory.getLogger(IngredienteResource.class);

    private final IngredienteService service;

    @PostMapping
    @ApiOperation(value = "Cadastra um novo Ingrediente em uma Receita")
    public ResponseEntity<SimpleEntityDTO> create(@PathVariable Long receitaId,
                                                  @Valid @RequestBody CreateUpdateIngredienteDTO ingredienteDTO) {
        logger.info("REST request to create a new Ingrediente [dto: {0}] in the Receita [id: {1}]", ingredienteDTO, receitaId);

        SimpleEntityDTO simpleEntityDTO = service.create(receitaId, ingredienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca um Ingrediente de uma Receita")
    public ResponseEntity<IngredienteDTO> get(@PathVariable Long receitaId, @PathVariable Long id) {
        logger.info("REST request to get an Ingrediente [id: {0}] from Receita [id: {1}]", id, receitaId);

        IngredienteDTO ingredienteDTO = service.get(receitaId, id);

        return ResponseEntity.ok(ingredienteDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Altera o Ingrediente de uma Receita")
    public ResponseEntity<Void> update(@PathVariable Long receitaId, @PathVariable Long id,
                                       @Valid @RequestBody CreateUpdateIngredienteDTO ingredienteDTO) {
        logger.info("REST request to update an Ingrediente [id: {0]] [dto: {1]] from Receita [id: {2}]", id, ingredienteDTO, receitaId);

        service.update(receitaId, id, ingredienteDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiOperation(value = "Busca todos os Ingredientes de uma Receita")
    public ResponseEntity<List<IngredienteDTO>> gelAll(@PathVariable Long receitaId) {
        logger.info("REST request to gel all Ingredientes of the Receita [id: {}]", receitaId);

        List<IngredienteDTO> ingredientes = service.getAll(receitaId);

        return ResponseEntity.ok().body(ingredientes);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove o Ingrediente de uma Receita")
    public ResponseEntity<Void> delete(@PathVariable Long receitaId, @PathVariable Long id) {
        logger.info("REST request do delete a Ingrediente [id: {0}] from Receita [id: {1}]", id, receitaId);

        service.delete(receitaId, id);

        return ResponseEntity.ok().build();
    }
}
