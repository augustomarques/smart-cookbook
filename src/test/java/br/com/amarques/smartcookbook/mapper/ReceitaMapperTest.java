package br.com.amarques.smartcookbook.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;

class ReceitaMapperTest {

    @Test
    void shouldConvertDTOToEntity() {
        CreateUpdateReceitaDTO dto = new CreateUpdateReceitaDTO("Arroz branco", "Modo de preparo do arroz");

        Receita receita = ReceitaMapper.toEntity(dto);

        assertNotNull(receita);
        assertNull(receita.getId());
        assertThat(receita.getNome(), is(equalTo(dto.nome)));
        assertThat(receita.getModoPreparo(), is(equalTo(dto.modoPreparo)));
    }

    @Test
    void shouldConvertEntityToDTO() {
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Arroz branco");
        receita.setModoPreparo("Modo de preparo do arroz");

        ReceitaDTO receitaDTO = ReceitaMapper.toDTO(receita);

        assertNotNull(receitaDTO);
        assertThat(receitaDTO.id, is(equalTo(receita.getId())));
        assertThat(receitaDTO.nome, is(equalTo(receita.getNome())));
        assertThat(receitaDTO.modoPreparo, is(equalTo(receita.getModoPreparo())));
    }
}
