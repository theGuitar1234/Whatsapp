package org.whatsapp.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.whatsapp.springboot.utilities.configurations.WhatsAppProperties;

@SpringBootApplication
@EnableConfigurationProperties(WhatsAppProperties.class)
public class SpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
