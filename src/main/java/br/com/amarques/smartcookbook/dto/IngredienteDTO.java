package br.com.amarques.smartcookbook.dto;

public class IngredienteDTO {

    public final Long id;
    public final String nome;

    public IngredienteDTO(final Long id, final String nome) {
        this.id = id;
        this.nome = nome;
    }
}
