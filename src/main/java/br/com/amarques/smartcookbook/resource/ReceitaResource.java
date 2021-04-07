package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;
import br.com.amarques.smartcookbook.service.ReceitaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Api(value = "Receitas", tags = {"Receitas Resource"})
@RestController
@RequestMapping("/receitas")
public class ReceitaResource {

    private final Logger logger = LoggerFactory.getLogger(ReceitaResource.class);

    private final ReceitaService service;

    @PostMapping
    @ApiOperation(value = "Cadastra uma nova Receita")
    public ResponseEntity<SimpleEntityDTO> create(@Valid @RequestBody CreateUpdateReceitaDTO receitaDTO) {
        logger.info("REST request to create a new Receita {}", receitaDTO);

        SimpleEntityDTO simpleEntityDTO = service.create(receitaDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca uma Receita pelo ID")
    public ResponseEntity<ReceitaDTO> get(@PathVariable Long id) {
        logger.info("REST request to get an Receita [id: {}]", id);

        ReceitaDTO receitaDTO = service.get(id);

        return ResponseEntity.ok(receitaDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Altera uma Receita cadastrada")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CreateUpdateReceitaDTO receitaDTO) {
        logger.info("REST request to update an Receita [id: {0]] [dto: {1]]", id, receitaDTO);

        service.update(id, receitaDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiOperation(value = "Busca todas das receitas")
    public ResponseEntity<List<ReceitaDTO>> gelAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                   @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        logger.info("REST request to gel all Receitas [Page: {0} - Size: {1}]", page, size);

        List<ReceitaDTO> receitas = service.getAll(PageRequest.of(page, size));

        return ResponseEntity.ok().body(receitas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("REST request to delete an Receita [id: {}] and all Ingredientes", id);

        service.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ReceitaDTO>> findByIngredientes(@RequestParam List<String> ingredientes) {
        logger.info("REST request to get Receitas from Ingredientes [{}]", ingredientes);

        List<ReceitaDTO> receitas =  service.findByIngredientes(ingredientes);

        return ResponseEntity.ok().body(receitas);
    }
}
