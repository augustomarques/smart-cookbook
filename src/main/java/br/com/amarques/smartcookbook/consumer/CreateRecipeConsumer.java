package br.com.amarques.smartcookbook.consumer;

import br.com.amarques.smartcookbook.dto.createupdate.CreateUpdateRecipeDTO;
import br.com.amarques.smartcookbook.dto.message.CreateRecipeMessageDTO;
import br.com.amarques.smartcookbook.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class CreateRecipeConsumer {

    private final RecipeService service;

    private void handler(final Message<CreateRecipeMessageDTO> message) {
        final var nome = (String) message.getHeaders().get("name");
        final var consumerMessageDTO = message.getPayload();
        final var createUpdateReceitaDTO = new CreateUpdateRecipeDTO(nome, consumerMessageDTO.recipe);

        service.create(createUpdateReceitaDTO);
    }

    @Bean
    public Consumer<Message<CreateRecipeMessageDTO>> saveReceitaEvent() {
        return message -> handler(message);
    }
}
