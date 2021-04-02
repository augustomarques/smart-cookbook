package br.com.amarques.smartcookbook.dto;

public class ReceitaDTO {

    public final Long id;
    public final String nome;
    public final String modoPreparo;

    public ReceitaDTO(final Long id, final String nome, final String modoPreparo) {
        this.id = id;
        this.nome = nome;
        this.modoPreparo = modoPreparo;
    }
}
