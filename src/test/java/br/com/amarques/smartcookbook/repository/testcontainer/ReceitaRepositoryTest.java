package br.com.amarques.smartcookbook.repository.testcontainer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.amarques.smartcookbook.domain.Receita;
import br.com.amarques.smartcookbook.repository.ReceitaRepository;

class ReceitaRepositoryTest extends TestcontainerRepositoryTestIT {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Test
    @Sql("/scripts/init_two_receitas_and_three_ingredientes.sql")
    void shouldReturnTheReceitaThatContainsTheIngredienteThatWasSought() {
        List<Receita> receitasQuemTemArroz = receitaRepository.findAllByIngredientes("Arroz");
        assertNotNull(receitasQuemTemArroz);
        assertThat(receitasQuemTemArroz.size(), is(equalTo(1)));
        assertThat(receitasQuemTemArroz.get(0).getNome(), is(equalTo("Arroz branco")));
    }

    @Test
    @Sql("/scripts/init_two_receitas_and_three_ingredientes.sql")
    void shouldReturnTheReceitasThatContainAllTheIngredientesThatWereSearched() {
        List<Receita> receitasQuemTemArrozOuFeijão = receitaRepository.findAllByIngredientes("Arroz|Feijão");
        assertNotNull(receitasQuemTemArrozOuFeijão);
        assertThat(receitasQuemTemArrozOuFeijão.size(), is(equalTo(2)));
        assertThat(receitasQuemTemArrozOuFeijão.get(0).getNome(), is(equalTo("Arroz branco")));
        assertThat(receitasQuemTemArrozOuFeijão.get(1).getNome(), is(equalTo("Feijão")));
    }
}
