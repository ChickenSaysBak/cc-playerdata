package me.chickensaysbak.ccplayerdata.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI config() {

        Contact contact = new Contact()
                .name("CozyCloud")
                .url("https://discord.gg/vBEWAuY");

        Info info = new Info()
                .title("CozyCloud Playerdata API")
                .description("API for retrieving CozyCloud playerdata.")
                .version("1.0.0")
                .contact(contact);

        return new OpenAPI().info(info);

    }

}
