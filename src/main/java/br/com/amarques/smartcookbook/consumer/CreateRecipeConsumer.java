package br.com.amarques.smartcookbook.consumer;

import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.usecase.recipe.CreateRecipeUseCase;
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

    private final CreateRecipeUseCase createRecipeUseCase;

    private void handler(final Message<CreateRecipeMessageDTO> message) {
        log.info("Message received for registration of new recipe");

        createRecipeUseCase.create(message.getPayload());
    }

    @Bean
    public Consumer<Message<CreateRecipeMessageDTO>> createRecipeEvent() {
        return this::handler;
    }

}
