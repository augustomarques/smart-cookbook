package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.RecipeDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.exception.FindByIngredientsException;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import br.com.amarques.smartcookbook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    private RecipeService recipeService;

    @BeforeEach
    public void before() {
        this.recipeService = new RecipeService(recipeRepository, ingredientRepository);
    }

    @Test
    void shouldFindByIDAndReturnDTO() {
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(buildReceita()));

        RecipeDTO recipeDTO = recipeService.get(ID);

        assertNotNull(recipeDTO);
        assertThat(recipeDTO.id, is(equalTo(ID)));
        assertThat(recipeDTO.name, is(equalTo(NOME)));
        assertThat(recipeDTO.wayOfDoing, is(equalTo(MODO_DE_PREPARO)));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFindByIDNotRegistered() {
        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> recipeService.get(ID));

        assertEquals(exception.getMessage(), MessageFormat.format("Receita [id: {0}] not found", ID));
    }

    @Test
    void shouldFindAllAndReturnNone() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Recipe> recipes = List.of();
        Page<Recipe> pageOfReceita = new PageImpl<>(recipes.subList(0, 0), pageable, recipes.size());

        when(recipeRepository.findAll(pageable)).thenReturn(pageOfReceita);

        List<RecipeDTO> receitasDTO = recipeService.getAll(pageable);

        assertTrue(receitasDTO.isEmpty());
    }

    @Test
    void shouldFindAllAndReturnTwoReceitas() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Recipe> recipes = List.of(buildReceita(), buildReceita());

        Page<Recipe> pageOfReceita = new PageImpl<>(recipes.subList(0, 2), pageable, recipes.size());
        when(recipeRepository.findAll(pageable)).thenReturn(pageOfReceita);

        List<RecipeDTO> receitasDTO = recipeService.getAll(pageable);

        assertFalse(receitasDTO.isEmpty());
        assertThat(receitasDTO.size(), is(equalTo(2)));
    }

    @Test
    void shouldCreateANewEmpresa() {
        CreateUpdateRecipeDTO dto = new CreateUpdateRecipeDTO(NOME, MODO_DE_PREPARO);
        Recipe recipe = buildReceita();
        recipe.setId(null);

        recipeService.create(dto);

        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void shouldUpdate() {
        CreateUpdateRecipeDTO dto = new CreateUpdateRecipeDTO("novo nome", "novo modo de preparo");

        when(recipeRepository.findById(ID)).thenReturn(Optional.of(buildReceita()));

        recipeService.update(ID, dto);

        Recipe recipe = buildReceita();
        recipe.setName("novo nome");
        recipe.setWayOfDoing("novo modo de preparo");

        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void shouldRemoveReceitaAndAllIngredientes() {
        recipeService.delete(ID);

        verify(ingredientRepository, times(1)).deleteByRecipeId(ID);
        verify(recipeRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldThrowAnExceptionWhenNoIngredientIsReported() {
        Exception exception = assertThrows(FindByIngredientsException.class, () -> recipeService.findByIngredients(
                List.of()));

        assertEquals("It is necessary to inform at least one Ingrediente", exception.getMessage());
    }

    @Test
    void shouldGenerateTheSearchParametersCorrectly() {
        List<String> ingredientes = List.of("Ingrediante 1", "Ingrediante 2");
        String queryParameter = "Ingrediante 1|Ingrediante 2";
        recipeService.findByIngredients(ingredientes);

        verify(recipeRepository, times(1)).findAllByIngredients(queryParameter);
    }

    public static Recipe buildReceita() {
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setName(NOME);
        recipe.setWayOfDoing(MODO_DE_PREPARO);
        return recipe;
    }

    private static final Long ID = 123L;
    private static final String NOME = "Arroz branco";
    private static final String MODO_DE_PREPARO = "Modo de preparo do arroz...";
}
