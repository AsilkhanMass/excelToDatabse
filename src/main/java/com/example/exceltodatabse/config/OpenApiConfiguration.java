package com.example.exceltodatabse.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "TTZ items management",
                description = """
                            This project allows to manage user register-login
                            and gives to specific ones with specific roles
                            to interact with items (add, delete, update)
                        """,
                termsOfService = "http://agrotechklaster.uz/",
                contact = @Contact(
                        name = "Ali",
                        email = "ttz@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://apache.com"
                ),
                version = "${spring.application.version}",
                summary = """
                        This project makes people lives
                        more easy
                        """
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "LOCAL"
                ),
                @Server(
                        url = "https://devspring.sec-g34.uz",
                        description = "DEVELOPMENT"
                ),
                @Server(
                        url = "https://spring.sec-g34.uz",
                        description = "PRODUCTION"
                )
        }
)
@Configuration
public class OpenApiConfiguration {
}
