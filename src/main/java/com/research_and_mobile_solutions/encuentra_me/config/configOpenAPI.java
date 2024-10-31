package com.research_and_mobile_solutions.encuentra_me.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configOpenAPI {
    @Bean
    public OpenAPI encuentraMeOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("EncuentraMe API")
                        .description("Aplicación móvil para encontrar personas desaparecidas"));
    }
}
