package com.app.med.project.api.controller;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
		@NotBlank
		String login,
		
		@NotBlank
		String senha) {

}
