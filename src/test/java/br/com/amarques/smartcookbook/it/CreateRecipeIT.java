package br.com.amarques.smartcookbook.it;

import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.usecase.recipe.GetRecipeUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.awaitility.Awaitility.await;

@Slf4j
class CreateRecipeIT extends BaseIT {

    @Value("${spring.cloud.stream.bindings.createRecipeEvent-in-0.destination}")
    private String saveRecipeEventTopic;

    @Autowired
    private GetRecipeUseCase getRecipeUseCase;

    @Autowired
    private StreamBridge streamBridge;

    private final ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @Test
    void should_register_a_new_recipe() throws Exception {
        final var name = "White Rice";
        final var wayOfDoing = "Add water and rice...";
        final var ingredients = List.of("Rice", "Garlic", "Water");
        final var createRecipeMessageDTO = new CreateRecipeMessageDTO(name, wayOfDoing, ingredients);
        final var jsonMessage = objectMapper.writeValueAsString(createRecipeMessageDTO);

        final Message<String> message = MessageBuilder.withPayload(jsonMessage).build();

        streamBridge.send(saveRecipeEventTopic, message);

        await().atMost(Duration.ofSeconds(20)).pollInterval(Duration.ofSeconds(1)).untilAsserted(() -> {
            log.info("Waiting for consumer to receive the message and process...");

            final var recipes = getRecipeUseCase.getAll(PageRequest.of(0, 10));

            assertThat(recipes)
                    .isNotEmpty()
                    .hasSize(1)
                    .extracting("name", "wayOfDoing")
                    .contains(tuple(name, wayOfDoing));
        });
    }

}
