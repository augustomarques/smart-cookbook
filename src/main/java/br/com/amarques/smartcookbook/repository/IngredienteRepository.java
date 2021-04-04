package br.com.amarques.smartcookbook.repository;

import br.com.amarques.smartcookbook.domain.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    Optional<Ingrediente> findByIdAndReceitaId(Long id, Long receitaId);

    List<Ingrediente> findAllByReceitaId(Long receitaId);

    void deleteByIdAndReceitaId(Long id, Long receitaId);

    void deleteByReceitaId(Long receitaId);
}
