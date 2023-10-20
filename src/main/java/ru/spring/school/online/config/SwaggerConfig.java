package ru.spring.school.online.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Online school server API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Makeev Roman",
                        email = "maker64@inbox.ru"
                )
        )
)
public class SwaggerConfig {
}
