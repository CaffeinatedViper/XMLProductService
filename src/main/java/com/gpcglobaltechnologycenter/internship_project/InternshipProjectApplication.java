package com.gpcglobaltechnologycenter.internship_project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Product API", version = "1.0", description = "API for managing products"))
public class InternshipProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternshipProjectApplication.class, args);
	}

}
