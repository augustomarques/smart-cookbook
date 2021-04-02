package br.com.amarques.smartcookbook.dto.createupdate;

import javax.validation.constraints.NotEmpty;

public class CreateUpdateIngredienteDTO {

    @NotEmpty(message = "O Nome é obrigatório")
    public final String nome;

    public CreateUpdateIngredienteDTO(){
        this.nome = null;
    }

    public CreateUpdateIngredienteDTO(final String nome) {
        this.nome = nome;
    }
}