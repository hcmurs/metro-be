package com.example.cronjob.Config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
   public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cron Job API")
                        .version("1.0.0")
                        .description("API documentation for the Cron Job application"));
    }
}
