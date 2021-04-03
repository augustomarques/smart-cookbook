package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.domain.Ingrediente;
import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredienteDTO;
import br.com.amarques.smartcookbook.mapper.IngredienteMapper;
import br.com.amarques.smartcookbook.service.IngredienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = IngredienteResource.class)
public class IngredienteReceitaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IngredienteService ingredienteService;

    @Test
    void shouldCreate() throws Exception {
        final Long receitaId = 222L;
        final Long ingredienteId = 222L;
        CreateUpdateIngredienteDTO ingredienteDTO = new CreateUpdateIngredienteDTO("Alho");
        when(ingredienteService.create(receitaId, ingredienteDTO)).thenReturn(new SimpleEntityDTO(ingredienteId));

        MvcResult mvcResult = mockMvc.perform(post("/receitas/{receitaId}/ingredientes", receitaId)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ingredienteDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        SimpleEntityDTO simpleEntityDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                SimpleEntityDTO.class);

        assertNotNull(simpleEntityDTO);
        assertThat(simpleEntityDTO.id, is(equalTo(receitaId)));
    }

    @Test
    public void shouldReturnIngredienteWhenFindByID() throws Exception {
        final Long receitaId = 1L;
        final Long ingredienteId = 20L;

        Ingrediente ingrediente = new Ingrediente(new Receita());
        ingrediente.setId(ingredienteId);

        when(ingredienteService.get(receitaId, ingredienteId)).thenReturn(IngredienteMapper.toDTO(ingrediente));

        MvcResult mvcResult = mockMvc.perform(get("/receitas/{receitaId}/ingredientes/{ingredienteId}", receitaId, ingredienteId))
                .andExpect(status().isOk())
                .andReturn();

        IngredienteDTO ingredienteDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), IngredienteDTO.class);

        assertNotNull(ingredienteDTO);
        assertThat(ingredienteDTO.id, is(equalTo(ingredienteId)));
    }

    @Test
    public void shouldReturnAllIngredientesFromReceitaID() throws Exception {
        final Long receitaId = 1L;
        final Long ingredienteId = 20L;

        Ingrediente ingrediente = new Ingrediente(new Receita());
        ingrediente.setId(ingredienteId);

        when(ingredienteService.getAll(receitaId)).thenReturn(List.of(IngredienteMapper.toDTO(ingrediente), IngredienteMapper.toDTO(ingrediente)));

        MvcResult mvcResult = mockMvc.perform(get("/receitas/{receitaId}/ingredientes", receitaId))
                .andExpect(status().isOk())
                .andReturn();

        List<IngredienteDTO> ingredientesDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, IngredienteDTO.class));

        assertNotNull(ingredientesDTO);
        assertThat(ingredientesDTO.size(), is(equalTo(2)));
    }

    @Test
    void shouldUpdate() throws Exception {
        final Long recitaId = 123L;
        final Long ingredienteId = 456L;
        CreateUpdateIngredienteDTO dto = new CreateUpdateIngredienteDTO("nome alterado");

        mockMvc.perform(put("/receitas/{receitaId}/ingredientes/{ingredienteId}", recitaId, ingredienteId)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(ingredienteService, times(1)).update(recitaId, ingredienteId, dto);
    }

    @Test
    void shouldDelete() throws Exception {
        final Long recitaId = 123L;
        final Long ingredienteId = 456L;

        mockMvc.perform(delete("/receitas/{receitaId}/ingredientes/{ingredienteId}", recitaId, ingredienteId)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        verify(ingredienteService, times(1)).delete(recitaId, ingredienteId);
    }
}
