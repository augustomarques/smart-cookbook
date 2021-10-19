package br.com.amarques.smartcookbook.it;

import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.dto.rest.RecipeDTO;
import br.com.amarques.smartcookbook.usecase.recipe.GetRecipeUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@Disabled
public class CreateRecipeIT extends BaseIT {

    @Value("${spring.cloud.stream.bindings.saveRecipeEvent-in-0.destination}")
    private String saveRecipeEventTopic;

    @Autowired
    private GetRecipeUseCase getRecipeUseCase;

    @Autowired
    private StreamBridge streamBridge;

    @Test
    public void teste() throws Exception {
        final var name = "White Rice";
        final var wayOfDoing = "Add water and rice...";
        final var ingredients = List.of("Rice", "Garlic", "Water");
        final var createRecipeMessageDTO = new CreateRecipeMessageDTO(name, wayOfDoing, ingredients);
        final var jsonMessage = new ObjectMapper().writeValueAsString(createRecipeMessageDTO);

        final Map<String, Object> headers = new HashMap<>();
        headers.put("name", name);
        final MessageHeaders messageHeaders = new MessageHeaders(headers);
        final Message<String> message = MessageBuilder.createMessage(jsonMessage, messageHeaders);
        streamBridge.send(saveRecipeEventTopic, message);

        //final Headers headers = new RecordHeaders();
        //headers.add(new RecordHeader("name", name.getBytes()));
        //producer.send(new ProducerRecord<>(saveRecipeEventTopic, null, UUID.randomUUID().toString(), jsonMessage, headers)).get();

        await().atMost(Duration.ofSeconds(10)).pollInterval(Duration.ofSeconds(1)).untilAsserted(() -> {
            log.info("Waiting for consumer to receive the message and process...");

            final List<RecipeDTO> recipes = getRecipeUseCase.getAll(PageRequest.of(0, 10));
            assertFalse(recipes.isEmpty());

            //final List<IngredientDTO> registeredIngredients = ingredientService.getAll(recipes.get(0).id);
            //assertNotNull(registeredIngredients);
        });
    }

}
