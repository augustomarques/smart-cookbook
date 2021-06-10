package br.com.amarques.smartcookbook.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class IngredienteTest {

    @Test
    void shouldCreateIngrediente() {
        Receita receita = new Receita();

        Ingrediente ingredienteArroz = new Ingrediente(receita);
        ingredienteArroz.setId(1L);
        ingredienteArroz.setNome("Arroz");

        Ingrediente ingredienteAlho = new Ingrediente(receita);
        ingredienteAlho.setId(2L);
        ingredienteAlho.setNome("Alho");

        assertNotNull(receita);
        assertNotNull(ingredienteArroz);
        assertNotNull(ingredienteAlho);

        assertThat(ingredienteArroz.getId(), is(equalTo(1L)));
        assertThat(ingredienteArroz.getNome(), is(equalTo("Arroz")));
        assertThat(ingredienteArroz.getReceita(), is(equalTo(receita)));

        assertThat(ingredienteAlho.getId(), is(equalTo(2L)));
        assertThat(ingredienteAlho.getNome(), is(equalTo("Alho")));
        assertThat(ingredienteAlho.getReceita(), is(equalTo(receita)));
    }
}
