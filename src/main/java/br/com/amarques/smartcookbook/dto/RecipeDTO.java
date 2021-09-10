package br.com.amarques.smartcookbook.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class RecipeDTO {

    public final Long id;
    public final String name;
    public final String wayOfDoing;

}
