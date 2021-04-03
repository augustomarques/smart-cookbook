package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Ingrediente;
import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredienteDTO;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.mapper.IngredienteMapper;
import br.com.amarques.smartcookbook.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository repository;
    private final ReceitaService receitaService;

    @Transactional
    public SimpleEntityDTO create(Long receitaId, CreateUpdateIngredienteDTO ingredienteDTO) {
        Receita receita = receitaService.findById(receitaId);
        Ingrediente ingrediente = IngredienteMapper.toEntity(ingredienteDTO, receita);

        repository.save(ingrediente);

        return new SimpleEntityDTO(ingrediente.getId());
    }

    public IngredienteDTO get(Long receitaId, Long id) {
        Ingrediente ingrediente = getByIdAndReceitaId(id, receitaId);
        return IngredienteMapper.toDTO(ingrediente);
    }

    @Transactional
    public void update(Long receitaId, Long id, CreateUpdateIngredienteDTO ingredienteDTO) {
        Ingrediente ingrediente = getByIdAndReceitaId(id, receitaId);
        ingrediente.setNome(ingredienteDTO.nome);
        repository.save(ingrediente);
    }

    private Ingrediente getByIdAndReceitaId(Long id, Long receitaId) {
        return repository.findByIdAndReceitaId(id, receitaId).orElseThrow(() -> new NotFoundException(MessageFormat.format(
                "Ingrediente [id: {0}] not found for Receita [id: {1}]", id, receitaId)));
    }

    public List<IngredienteDTO> getAll(Long receitaId) {
        List<Ingrediente> ingredientes = repository.findAllByReceitaId(receitaId);

        if (CollectionUtils.isEmpty(ingredientes)) {
            return List.of();
        }

        return ingredientes.stream().map(IngredienteMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long receitaId, Long id) {
        repository.deleteByIdAndReceitaId(id, receitaId);
    }
}
