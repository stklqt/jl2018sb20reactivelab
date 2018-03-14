package de.virtual7.reactivelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class ReactiveLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveLabApplication.class, args);
	}
}
