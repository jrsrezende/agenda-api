package br.com.jrsr.agendaapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI agendaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agenda API")
                        .version("1.0.0")
                        .description("API for managing tasks")
                        .contact(new Contact()
                                .name("João Rezende")
                                .email("joao@example.com")
                        ))
                .externalDocs(new ExternalDocumentation()
                        .description("Complete Documentation")
                        .url("https://github.com/jrsrezende/task-scheduling-api"));
    }
}

