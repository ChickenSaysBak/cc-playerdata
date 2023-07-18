package me.chickensaysbak.ccplayerdata;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public OpenAPI springShopOpenAPI() {

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
