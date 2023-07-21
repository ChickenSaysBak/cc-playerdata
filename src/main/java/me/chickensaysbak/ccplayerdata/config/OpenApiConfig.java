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
                .name("GitHub")
                .url("https://github.com/ChickenSaysBak/cc-playerdata");

        Info info = new Info()
                .title("CozyCloud Playerdata API")
                .description("REST API for retrieving CozyCloud playerdata. Find example usage on the GitHub page below.")
                .version("1.1.1")
                .contact(contact);

        return new OpenAPI().info(info);

    }

}
