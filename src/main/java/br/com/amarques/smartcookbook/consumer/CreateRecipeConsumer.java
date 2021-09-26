package br.com.amarques.smartcookbook.consumer;

import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.service.CreateRecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateRecipeConsumer {

    private final CreateRecipeService createRecipeService;

    @Bean
    public Consumer<Message<CreateRecipeMessageDTO>> saveRecipeEvent() {
        return message -> handler(message);
    }

    private void handler(final Message<CreateRecipeMessageDTO> message) {
        final var name = (String) message.getHeaders().get("name");
        final var createRecipeMessageDTO = message.getPayload();

        log.info(String.format("RecipeConsumer :: Create a new Recipe [name: %s]", name));

        createRecipeService.create(name, createRecipeMessageDTO);
    }

}
