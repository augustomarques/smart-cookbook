package br.com.amarques.smartcookbook.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import br.com.amarques.smartcookbook.domain.Ingrediente;
import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredienteDTO;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.IngredienteRepository;

@ExtendWith(MockitoExtension.class)
class IngredienteServiceTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @Mock
    private ReceitaService receitaService;

    private IngredienteService ingredienteService;

    @BeforeEach
    public void before() {
        this.ingredienteService = new IngredienteService(ingredienteRepository, receitaService);
    }

    @Test
    void shouldReturnRegisteredIngrediente() {
        when(ingredienteRepository.findByIdAndReceitaId(INGREDIENTE_ID, RECEITA_ID))
                .thenReturn(Optional.of(buildIngrediente()));

        IngredienteDTO ingredienteDTO = ingredienteService.get(RECEITA_ID, INGREDIENTE_ID);

        assertNotNull(ingredienteDTO);
        assertThat(ingredienteDTO.id, is(equalTo(INGREDIENTE_ID)));
        assertThat(ingredienteDTO.nome, is(equalTo(NOME)));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFindUnregisteredID() {
        when(ingredienteRepository.findByIdAndReceitaId(INGREDIENTE_ID, RECEITA_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> ingredienteService.get(RECEITA_ID,
                INGREDIENTE_ID));

        assertEquals(exception.getMessage(), MessageFormat.format(
                "Ingrediente [id: {0}] not found for Receita [id: {1}]",
                INGREDIENTE_ID, RECEITA_ID));
    }

    @Test
    void shouldReturnAllIngredientesOfReceita() {
        when(ingredienteRepository.findAllByReceitaId(RECEITA_ID))
                .thenReturn(List.of(buildIngrediente(), buildIngrediente()));

        List<IngredienteDTO> ingredientes = ingredienteService.getAll(RECEITA_ID);

        assertNotNull(ingredientes);
        assertThat(ingredientes.size(), is(equalTo(2)));
    }

    @Test
    void shouldFindAllAndReturnNone() {
        when(ingredienteRepository.findAllByReceitaId(RECEITA_ID)).thenReturn(List.of());

        List<IngredienteDTO> ingredientes = ingredienteService.getAll(RECEITA_ID);

        assertTrue(ingredientes.isEmpty());
    }

    @Test
    void shouldCreateNewIngrediente() {
        when(receitaService.findById(RECEITA_ID)).thenReturn(new Receita());

        CreateUpdateIngredienteDTO ingredienteDTO = new CreateUpdateIngredienteDTO(NOME);

        SimpleEntityDTO simpleEntityDTO = ingredienteService.create(RECEITA_ID, ingredienteDTO);

        assertNotNull(simpleEntityDTO);
    }

    @Test
    void shouldUpdateIngrediente() {
        when(ingredienteRepository.findByIdAndReceitaId(INGREDIENTE_ID, RECEITA_ID))
                .thenReturn(Optional.of(buildIngrediente()));

        CreateUpdateIngredienteDTO ingredienteDTO = new CreateUpdateIngredienteDTO("novo nome ingrediente");

        Ingrediente ingrediente = buildIngrediente();
        ingrediente.setNome(ingredienteDTO.nome);

        ingredienteService.update(RECEITA_ID, INGREDIENTE_ID, ingredienteDTO);

        verify(ingredienteRepository, times(1)).save(ingrediente);
    }

    @Test
    void shouldDelete() {
        ingredienteService.delete(RECEITA_ID, INGREDIENTE_ID);

        verify(ingredienteRepository, times(1)).deleteByIdAndReceitaId(INGREDIENTE_ID, RECEITA_ID);
    }

    public static Ingrediente buildIngrediente() {
        Ingrediente ingrediente = new Ingrediente(RECEITA);
        ingrediente.setId(INGREDIENTE_ID);
        ingrediente.setNome(NOME);
        return ingrediente;
    }

    private static final Long RECEITA_ID = 999L;
    private static final Long INGREDIENTE_ID = 123L;
    private static final String NOME = "Arroz";
    private static final Receita RECEITA = new Receita();
}
