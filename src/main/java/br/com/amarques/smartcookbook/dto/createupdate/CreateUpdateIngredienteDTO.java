package br.com.amarques.smartcookbook.dto.createupdate;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateUpdateIngredienteDTO {

    @NotBlank(message = "O Nome é obrigatório")
    public final String nome;

}
