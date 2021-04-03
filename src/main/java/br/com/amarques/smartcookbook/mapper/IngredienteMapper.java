package br.com.amarques.smartcookbook.mapper;

import br.com.amarques.smartcookbook.domain.Ingrediente;
import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredienteDTO;

public class IngredienteMapper {

    private IngredienteMapper() {}

    public static Ingrediente toEntity(CreateUpdateIngredienteDTO dto, Receita receita) {
        Ingrediente ingrediente = new Ingrediente(receita);
        ingrediente.setNome(dto.nome);
        return ingrediente;
    }

    public static IngredienteDTO toDTO(Ingrediente ingrediente) {
        return new IngredienteDTO(ingrediente.getId(), ingrediente.getNome());
    }
}
