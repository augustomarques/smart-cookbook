package br.com.amarques.smartcookbook.dto.createupdate;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateUpdateIngredienteDTO {

    @NotEmpty(message = "O Nome é obrigatório")
    public final String nome;

}