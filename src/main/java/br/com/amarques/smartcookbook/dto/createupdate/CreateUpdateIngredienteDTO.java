package br.com.amarques.smartcookbook.dto.createupdate;

public class CreateUpdateIngredienteDTO {

    public final String nome;

    public CreateUpdateIngredienteDTO(){
        this.nome = null;
    }

    public CreateUpdateIngredienteDTO(final String nome) {
        this.nome = nome;
    }
}