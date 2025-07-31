package com.shoppingorderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShoppingOrderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingOrderApiApplication.class, args);
	}

}
