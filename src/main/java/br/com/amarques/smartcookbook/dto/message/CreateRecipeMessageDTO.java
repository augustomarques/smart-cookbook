package br.com.amarques.smartcookbook.dto.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateRecipeMessageDTO {

    public final String wayOfDoing;
    public final List<String> ingredients;

}
