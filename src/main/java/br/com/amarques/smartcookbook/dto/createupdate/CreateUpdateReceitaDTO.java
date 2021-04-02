package br.com.amarques.smartcookbook.dto.createupdate;

import javax.validation.constraints.NotEmpty;

public class CreateUpdateReceitaDTO {

    @NotEmpty(message = "O Nome é obrigatório")
    public final String nome;
    @NotEmpty(message = "O Modo de Preparo é obrigatório")
    public final String modoPreparo;

    public CreateUpdateReceitaDTO() {
        this.nome = null;
        this.modoPreparo = null;
    }

    public CreateUpdateReceitaDTO(final String nome, final String modoPreparo) {
        this.nome = nome;
        this.modoPreparo = modoPreparo;
    }
}
