package br.com.amarques.smartcookbook.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;
import br.com.amarques.smartcookbook.exception.FindByIngredientesException;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.IngredienteRepository;
import br.com.amarques.smartcookbook.repository.ReceitaRepository;

@ExtendWith(MockitoExtension.class)
class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private IngredienteRepository ingredienteRepository;

    private ReceitaService receitaService;

    @BeforeEach
    public void before() {
        this.receitaService = new ReceitaService(receitaRepository, ingredienteRepository);
    }

    @Test
    void shouldFindByIDAndReturnDTO() {
        when(receitaRepository.findById(ID)).thenReturn(Optional.of(buildReceita()));

        ReceitaDTO receitaDTO = receitaService.get(ID);

        assertNotNull(receitaDTO);
        assertThat(receitaDTO.id, is(equalTo(ID)));
        assertThat(receitaDTO.nome, is(equalTo(NOME)));
        assertThat(receitaDTO.modoPreparo, is(equalTo(MODO_DE_PREPARO)));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFindByIDNotRegistered() {
        when(receitaRepository.findById(ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> receitaService.get(ID));

        assertEquals(exception.getMessage(), MessageFormat.format("Receita [id: {0}] not found", ID));
    }

    @Test
    void shouldFindAllAndReturnNone() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Receita> receitas = List.of();
        Page<Receita> pageOfReceita = new PageImpl<>(receitas.subList(0, 0), pageable, receitas.size());

        when(receitaRepository.findAll(pageable)).thenReturn(pageOfReceita);

        List<ReceitaDTO> receitasDTO = receitaService.getAll(pageable);

        assertTrue(receitasDTO.isEmpty());
    }

    @Test
    void shouldFindAllAndReturnTwoReceitas() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Receita> receitas = List.of(buildReceita(), buildReceita());

        Page<Receita> pageOfReceita = new PageImpl<>(receitas.subList(0, 2), pageable, receitas.size());
        when(receitaRepository.findAll(pageable)).thenReturn(pageOfReceita);

        List<ReceitaDTO> receitasDTO = receitaService.getAll(pageable);

        assertFalse(receitasDTO.isEmpty());
        assertThat(receitasDTO.size(), is(equalTo(2)));
    }

    @Test
    void shouldCreateANewEmpresa() {
        CreateUpdateReceitaDTO dto = new CreateUpdateReceitaDTO(NOME, MODO_DE_PREPARO);
        Receita receita = buildReceita();
        receita.setId(null);

        receitaService.create(dto);

        verify(receitaRepository, times(1)).save(receita);
    }

    @Test
    void shouldUpdate() {
        CreateUpdateReceitaDTO dto = new CreateUpdateReceitaDTO("novo nome", "novo modo de preparo");

        when(receitaRepository.findById(ID)).thenReturn(Optional.of(buildReceita()));

        receitaService.update(ID, dto);

        Receita receita = buildReceita();
        receita.setNome("novo nome");
        receita.setModoPreparo("novo modo de preparo");

        verify(receitaRepository, times(1)).save(receita);
    }

    @Test
    void shouldRemoveReceitaAndAllIngredientes() {
        receitaService.delete(ID);

        verify(ingredienteRepository, times(1)).deleteByReceitaId(ID);
        verify(receitaRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldThrowAnExceptionWhenNoIngredientIsReported() {
        Exception exception = assertThrows(FindByIngredientesException.class, () -> receitaService.findByIngredientes(
                List.of()));

        assertEquals("It is necessary to inform at least one Ingrediente", exception.getMessage());
    }

    @Test
    void shouldGenerateTheSearchParametersCorrectly() {
        List<String> ingredientes = List.of("Ingrediante 1", "Ingrediante 2");
        String queryParameter = "Ingrediante 1|Ingrediante 2";
        receitaService.findByIngredientes(ingredientes);

        verify(receitaRepository, times(1)).findAllByIngredientes(queryParameter);
    }

    public static Receita buildReceita() {
        Receita receita = new Receita();
        receita.setId(ID);
        receita.setNome(NOME);
        receita.setModoPreparo(MODO_DE_PREPARO);
        return receita;
    }

    private static final Long ID = 123L;
    private static final String NOME = "Arroz branco";
    private static final String MODO_DE_PREPARO = "Modo de preparo do arroz...";
}
