package br.com.amarques.smartcookbook.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.com.amarques.smartcookbook.domain.Ingrediente;
import br.com.amarques.smartcookbook.domain.Receita;

class ReceitaRepositoryTest extends RepositoryBaseIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Test
    void mustReturnTheRecipesThatContainTheIngredientSought() {
        Receita receitaArroz = new Receita();
        receitaArroz.setNome("Arroz branco");
        receitaArroz.setModoPreparo("Modo de preparo do Arroz");

        Receita receitaFeijao = new Receita();
        receitaFeijao.setNome("Feijão");
        receitaFeijao.setModoPreparo("Modo de preparo do Feijão");

        entityManager.persist(receitaArroz);
        entityManager.persist(receitaFeijao);

        Ingrediente ingredienteArroz = new Ingrediente(receitaArroz);
        ingredienteArroz.setNome("Arroz");
        Ingrediente ingredienteAlho = new Ingrediente(receitaArroz);
        ingredienteAlho.setNome("Alho");
        Ingrediente ingredienteFeijao = new Ingrediente(receitaFeijao);
        ingredienteFeijao.setNome("Feijão");

        entityManager.persist(ingredienteArroz);
        entityManager.persist(ingredienteAlho);
        entityManager.persist(ingredienteFeijao);

        List<Receita> receitasQuemTemArroz = receitaRepository.findAllByIngredientes("Arroz");
        assertNotNull(receitasQuemTemArroz);
        assertThat(receitasQuemTemArroz.size(), is(equalTo(1)));
        assertThat(receitasQuemTemArroz.get(0).getNome(), is(equalTo("Arroz branco")));

        List<Receita> receitasQuemTemArrozOuFeijão = receitaRepository.findAllByIngredientes("Arroz|Feijão");
        assertNotNull(receitasQuemTemArrozOuFeijão);
        assertThat(receitasQuemTemArrozOuFeijão.size(), is(equalTo(2)));
        assertThat(receitasQuemTemArrozOuFeijão.get(0).getNome(), is(equalTo("Arroz branco")));
        assertThat(receitasQuemTemArrozOuFeijão.get(1).getNome(), is(equalTo("Feijão")));
    }
}
