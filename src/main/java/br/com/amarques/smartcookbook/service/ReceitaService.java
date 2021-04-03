package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.mapper.ReceitaMapper;
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
}
