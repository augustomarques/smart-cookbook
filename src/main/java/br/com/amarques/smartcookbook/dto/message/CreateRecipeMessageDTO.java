package br.com.amarques.smartcookbook.dto.message;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateRecipeMessageDTO {

    public final String wayOfDoing;

    public final List<String> ingredients;

}
