package br.com.amarques.smartcookbook.repository;

import br.com.amarques.smartcookbook.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByIdAndRecipeId(Long id, Long recipeId);

    List<Ingredient> findAllByRecipeId(Long recipeId);

    void deleteByIdAndRecipeId(Long id, Long recipeId);

    void deleteByRecipeId(Long recipeId);
}
