package br.com.amarques.smartcookbook.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.amarques.smartcookbook.domain.Ingrediente;
import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredienteDTO;

class IngredienteMapperTest {

    @Test
    void shouldConvertDTOToEntity() {
        CreateUpdateIngredienteDTO dto = new CreateUpdateIngredienteDTO("Arroz");
        Receita receita = new Receita();

        Ingrediente ingrediente = IngredienteMapper.toEntity(dto, receita);

        assertNotNull(ingrediente);
        assertNull(ingrediente.getId());
        assertThat(ingrediente.getNome(), is(equalTo(dto.nome)));
        assertThat(ingrediente.getReceita(), is(equalTo(receita)));
    }

    @Test
    void shouldConvertEntityToDTO() {
        Receita receita = new Receita();
        Ingrediente ingrediente = new Ingrediente(receita);
        ingrediente.setId(1L);
        ingrediente.setNome("Arroz");

        IngredienteDTO ingredienteDTO = IngredienteMapper.toDTO(ingrediente);

        assertNotNull(ingredienteDTO);
        assertThat(ingredienteDTO.id, is(equalTo(ingrediente.getId())));
        assertThat(ingredienteDTO.nome, is(equalTo(ingrediente.getNome())));
    }
}
