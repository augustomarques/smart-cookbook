package br.com.amarques.smartcookbook.repository;

import br.com.amarques.smartcookbook.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query(value = "SELECT r.id, r.nome, r.modo_preparo FROM receitas r " +
            "INNER JOIN ingredientes i ON r.id = i.receita_id " +
            "WHERE i.nome REGEXP ?1 " +
            "GROUP BY r.id", nativeQuery = true)
    List<Receita> findAllByIngredientes(String queryParameters);
}
