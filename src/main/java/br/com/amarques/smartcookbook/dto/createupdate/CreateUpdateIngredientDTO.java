package br.com.amarques.smartcookbook.dto.createupdate;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateUpdateIngredientDTO {

    @NotBlank(message = "The name is required")
    public final String name;

}
