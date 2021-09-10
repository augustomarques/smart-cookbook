package br.com.amarques.smartcookbook.dto.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateRecipeMessageDTO {

    public final String recipe;

}
