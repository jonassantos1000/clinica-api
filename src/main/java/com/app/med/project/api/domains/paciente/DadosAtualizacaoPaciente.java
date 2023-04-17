package com.app.med.project.api.domains.paciente;

import com.app.med.project.api.domains.endereco.DadosEndereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPaciente(		
		@NotNull
		Long id,

		@NotBlank
		String telefone,
		
		@NotBlank
		String email,

		@NotNull
		DadosEndereco endereco) {
	
}
