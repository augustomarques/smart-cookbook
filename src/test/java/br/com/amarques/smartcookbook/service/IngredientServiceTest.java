package br.com.amarques.smartcookbook.service;

import br.com.amarques.smartcookbook.domain.Ingredient;
import br.com.amarques.smartcookbook.domain.Recipe;
import br.com.amarques.smartcookbook.dto.IngredientDTO;
import br.com.amarques.smartcookbook.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateIngredientDTO;
import br.com.amarques.smartcookbook.exception.NotFoundException;
import br.com.amarques.smartcookbook.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeService recipeService;

    private IngredientService ingredientService;

    @BeforeEach
    public void before() {
        this.ingredientService = new IngredientService(ingredientRepository, recipeService);
    }

    @Test
    void shouldReturnRegisteredIngrediente() {
        when(ingredientRepository.findByIdAndRecipeId(INGREDIENTE_ID, RECEITA_ID))
                .thenReturn(Optional.of(buildIngrediente()));

        IngredientDTO ingredientDTO = ingredientService.get(RECEITA_ID, INGREDIENTE_ID);

        assertNotNull(ingredientDTO);
        assertThat(ingredientDTO.id, is(equalTo(INGREDIENTE_ID)));
        assertThat(ingredientDTO.name, is(equalTo(NOME)));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFindUnregisteredID() {
        when(ingredientRepository.findByIdAndRecipeId(INGREDIENTE_ID, RECEITA_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> ingredientService.get(RECEITA_ID,
                INGREDIENTE_ID));

        assertEquals(exception.getMessage(), MessageFormat.format(
                "Ingrediente [id: {0}] not found for Receita [id: {1}]",
                INGREDIENTE_ID, RECEITA_ID));
    }

    @Test
    void shouldReturnAllIngredientesOfReceita() {
        when(ingredientRepository.findAllByRecipeId(RECEITA_ID))
                .thenReturn(List.of(buildIngrediente(), buildIngrediente()));

        List<IngredientDTO> ingredientes = ingredientService.getAll(RECEITA_ID);

        assertNotNull(ingredientes);
        assertThat(ingredientes.size(), is(equalTo(2)));
    }

    @Test
    void shouldFindAllAndReturnNone() {
        when(ingredientRepository.findAllByRecipeId(RECEITA_ID)).thenReturn(List.of());

        List<IngredientDTO> ingredientes = ingredientService.getAll(RECEITA_ID);

        assertTrue(ingredientes.isEmpty());
    }

    @Test
    void shouldCreateNewIngrediente() {
        when(recipeService.findById(RECEITA_ID)).thenReturn(new Recipe());

        CreateUpdateIngredientDTO ingredienteDTO = new CreateUpdateIngredientDTO(NOME);

        SimpleEntityDTO simpleEntityDTO = ingredientService.create(RECEITA_ID, ingredienteDTO);

        assertNotNull(simpleEntityDTO);
    }

    @Test
    void shouldUpdateIngrediente() {
        when(ingredientRepository.findByIdAndRecipeId(INGREDIENTE_ID, RECEITA_ID))
                .thenReturn(Optional.of(buildIngrediente()));

        CreateUpdateIngredientDTO ingredienteDTO = new CreateUpdateIngredientDTO("novo nome ingrediente");

        Ingredient ingredient = buildIngrediente();
        ingredient.setName(ingredienteDTO.name);

        ingredientService.update(RECEITA_ID, INGREDIENTE_ID, ingredienteDTO);

        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void shouldDelete() {
        ingredientService.delete(RECEITA_ID, INGREDIENTE_ID);

        verify(ingredientRepository, times(1)).deleteByIdAndRecipeId(INGREDIENTE_ID, RECEITA_ID);
    }

    public static Ingredient buildIngrediente() {
        Ingredient ingredient = new Ingredient(RECIPE);
        ingredient.setId(INGREDIENTE_ID);
        ingredient.setName(NOME);
        return ingredient;
    }

    private static final Long RECEITA_ID = 999L;
    private static final Long INGREDIENTE_ID = 123L;
    private static final String NOME = "Arroz";
    private static final Recipe RECIPE = new Recipe();
}
