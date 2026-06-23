package com.urbandelivery.promociones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI promocionesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Promociones")
                        .description("Microservicio UrbanDelivery para la gestión de descuentos con HATEOAS")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo UrbanDelivery")
                                .email("contacto@urbandelivery.cl")));
    }
}