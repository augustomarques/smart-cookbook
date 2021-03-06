package br.com.amarques.smartcookbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Receitas Inteligentes - Rest APIs")
                .version("Esta página lista todos os serviços REST da API de Receitas Inteligentes")
                .description("1.0.0"));
    }

    /**
     * Necessário para que o OpenAPI respeite o snake_case
     */
    @Bean
    public ModelResolver modelResolver(final ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
