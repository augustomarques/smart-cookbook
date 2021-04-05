package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;
import br.com.amarques.smartcookbook.exception.FindByIngredientesException;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.mapper.ReceitaMapper;
import br.com.amarques.smartcookbook.repository.IngredienteRepository;
import br.com.amarques.smartcookbook.repository.ReceitaRepository;
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
public class ReceitaService {

    private final ReceitaRepository repository;
    private final IngredienteRepository ingredienteRepository;

    @Transactional
    public SimpleEntityDTO create(CreateUpdateReceitaDTO receitaDTO) {
        Receita receita = ReceitaMapper.toEntity(receitaDTO);
        repository.save(receita);

        return new SimpleEntityDTO(receita.getId());
    }

    public ReceitaDTO get(Long id) {
        Receita receita = findById(id);
        return ReceitaMapper.toDTO(receita);
    }

    protected Receita findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(
                "Receita [id: {0}] not found", id)));
    }

    @Transactional
    public void update(Long id, CreateUpdateReceitaDTO receitaDTO) {
        Receita receita = findById(id);
        receita.setNome(receitaDTO.nome);
        receita.setModoPreparo(receitaDTO.modoPreparo);

        repository.save(receita);
    }

    public List<ReceitaDTO> getAll() {
        List<Receita> receitas = repository.findAll();

        if (CollectionUtils.isEmpty(receitas)) {
            return List.of();
        }

        return receitas.stream().map(ReceitaMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        ingredienteRepository.deleteByReceitaId(id);
        repository.deleteById(id);
    }

    public List<ReceitaDTO> findByIngredientes(List<String> ingredientes) {
        if(CollectionUtils.isEmpty(ingredientes)) {
            throw new FindByIngredientesException("It is necessary to inform at least one Ingrediente");
        }

        String queryParameters = buildIngredientesParameterRegex(ingredientes);

        List<Receita> receitas = repository.findAllByIngredientes(queryParameters);
        if (CollectionUtils.isEmpty(receitas)) {
            return List.of();
        }

        return receitas.stream().map(ReceitaMapper::toDTO).collect(Collectors.toList());
    }

    private static String buildIngredientesParameterRegex(List<String> ingredientes) {
        StringBuilder query = new StringBuilder();

        for(int i = 0; i < ingredientes.size(); i++) {
            String ingrediente = ingredientes.get(i);
            query.append(ingrediente);

            if(i < (ingredientes.size() - 1)) {
                query.append("|");
            }
        }

        return query.toString();
    }
}
