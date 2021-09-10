package br.com.amarques.smartcookbook.dto.createupdate;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateUpdateRecipeDTO {

    @NotBlank(message = "The [name] is required")
    public final String name;

    @NotBlank(message = "The [wayOfDoing] is required")
    public final String wayOfDoing;

}
