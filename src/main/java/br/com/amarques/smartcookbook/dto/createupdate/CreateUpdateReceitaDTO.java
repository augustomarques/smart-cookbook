package br.com.amarques.smartcookbook.dto.createupdate;

public class CreateUpdateReceitaDTO {

    public final String nome;
    public final String modoPreparo;

    public CreateUpdateReceitaDTO(final String nome, final String modoPreparo) {
        this.nome = nome;
        this.modoPreparo = modoPreparo;
    }
}
