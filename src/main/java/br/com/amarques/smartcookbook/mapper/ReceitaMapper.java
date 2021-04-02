package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateReceitaDTO;

public class ReceitaMapper {

    private ReceitaMapper() {}

    public static Receita toEntity(CreateUpdateReceitaDTO dto) {
        Receita receita = new Receita();
        receita.setNome(dto.nome);
        receita.setModoPreparo(dto.modoPreparo);
        return receita;
    }

    public static ReceitaDTO toDTO(Receita receita) {
        return new ReceitaDTO(receita.getId(), receita.getNome(), receita.getModoPreparo());
    }
}
