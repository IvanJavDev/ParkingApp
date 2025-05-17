package org.example.parkingapp.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class OpenApiV1Config {
    @Bean
    public OpenAPI parkingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parking Service API")
                        .description("API documentation for parking management system")
                        .version("1.0"))
                .addServersItem(new Server()
                        .description("API Version 1"));

    }
}
