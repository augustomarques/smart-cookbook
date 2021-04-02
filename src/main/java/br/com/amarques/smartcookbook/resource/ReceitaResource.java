package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;
import br.com.amarques.smartcookbook.service.ReceitaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaResource {

    private final Logger logger = LoggerFactory.getLogger(ReceitaResource.class);

    private final ReceitaService service;

    public ReceitaResource(final ReceitaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SimpleEntityDTO> create(@RequestBody CreateUpdateReceitaDTO receitaDTO) {
        logger.info("REST request to create a new Receita {}", receitaDTO);

        SimpleEntityDTO simpleEntityDTO = service.create(receitaDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> get(@PathVariable Long id) {
        logger.info("REST request to get an Receita [id: {}]", id);

        ReceitaDTO receitaDTO = service.get(id);

        return ResponseEntity.ok(receitaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CreateUpdateReceitaDTO receitaDTO) {
        logger.info("REST request to update an Receita [id: {0]] [dto: {1]]", id, receitaDTO);

        service.update(id, receitaDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReceitaDTO>> gelAll() {
        logger.info("REST request to gel all Receitas");

        List<ReceitaDTO> receitas = service.getAll();

        return ResponseEntity.ok().body(receitas);
    }
}
