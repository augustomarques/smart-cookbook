package br.com.amarques.smartcookbook.resource;

import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;
import br.com.amarques.smartcookbook.mapper.ReceitaMapper;
import br.com.amarques.smartcookbook.service.ReceitaService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReceitaResource.class)
public class ReceitaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReceitaService receitaService;

    @Test
    public void shouldReturnAllReceitasRegistered() throws Exception {
        Receita receita1 = new Receita();
        receita1.setId(1L);

        Receita receita2 = new Receita();
        receita2.setId(2L);

        when(receitaService.getAll()).thenReturn(List.of(ReceitaMapper.toDTO(receita1), ReceitaMapper.toDTO(receita2)));

        MvcResult mvcResult = mockMvc.perform(get("/receitas"))
                .andExpect(status().isOk())
                .andReturn();

        List<ReceitaDTO> receitas = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, ReceitaDTO.class));

        assertNotNull(receitas);
        assertEquals(2, receitas.size());
    }

    @Test
    public void shouldReturnReceitaWhenFindByID() throws Exception {
        final Long id = 1L;
        Receita receita1 = new Receita();
        receita1.setId(id);

        when(receitaService.get(id)).thenReturn(ReceitaMapper.toDTO(receita1));

        MvcResult mvcResult = mockMvc.perform(get("/receitas/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        ReceitaDTO receita = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), ReceitaDTO.class);

        assertNotNull(receita);
        assertThat(receita.id, is(equalTo(id)));
    }

    @Test
    void shouldCreate() throws Exception {
        final Long id = 222L;
        CreateUpdateReceitaDTO receitaDTO = new CreateUpdateReceitaDTO("Arroz branco", "Modo de preparo teste");
        when(receitaService.create(receitaDTO)).thenReturn(new SimpleEntityDTO(id));

        MvcResult mvcResult = mockMvc.perform(post("/receitas")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(receitaDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        SimpleEntityDTO simpleEntityDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                SimpleEntityDTO.class);

        assertNotNull(simpleEntityDTO);
        assertThat(simpleEntityDTO.id, is(equalTo(id)));
    }

    @Test
    void shouldUpdate() throws Exception {
        final Long id = 123L;
        CreateUpdateReceitaDTO dto = new CreateUpdateReceitaDTO("nome", "modo de preparo");

        mockMvc.perform(put("/receitas/{id}", id)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(receitaService, times(1)).update(id, dto);
    }
}
