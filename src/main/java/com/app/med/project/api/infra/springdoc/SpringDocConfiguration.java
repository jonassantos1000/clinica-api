package com.app.med.project.api.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("bearer-key",
								new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")))
				.info(new Info()
						.title("Clinica API")
						.description("API Rest contendo funcionalidades de CRUD de uma clinica medica, tais como: cadastro de clientes e medicos, inclusive com opção de agendamentos e cancelamento de consultas")
						.contact(new Contact()
                                .name("Jonas Silva")
                                .email("jonasvale3@hotmail.com"))
				.license(new License()
						.name("Apache 2.0")
						.url("http://clinica/api/licenca")));
	}

}
