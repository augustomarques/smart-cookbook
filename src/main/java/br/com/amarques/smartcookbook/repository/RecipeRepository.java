package br.com.amarques.smartcookbook.repository;

import br.com.amarques.smartcookbook.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT r.id, r.name, r.way_of_doing FROM recipes r " +
            "INNER JOIN ingredients i ON r.id = i.recipe_id " +
            "WHERE i.name REGEXP ?1 " +
            "GROUP BY r.id", nativeQuery = true)
    List<Recipe> findAllByIngredients(String queryParameters);
}
