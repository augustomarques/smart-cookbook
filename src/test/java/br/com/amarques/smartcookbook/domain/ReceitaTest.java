package br.com.amarques.smartcookbook.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ReceitaTest {

    @Test
    void shouldCreateReceita() {
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("NOME TESTE");
        receita.setModoPreparo("MODO DE PREPARO TESTE");

        assertNotNull(receita);
        assertThat(receita.getId(), is(equalTo(1L)));
        assertThat(receita.getNome(), is(equalTo("NOME TESTE")));
        assertThat(receita.getModoPreparo(), is(equalTo("MODO DE PREPARO TESTE")));
    }
}
