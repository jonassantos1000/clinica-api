package com.app.med.project.api.domains.paciente;

import com.app.med.project.api.domains.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPaciente(
		@NotBlank
		String nome, 
		
		@NotBlank
		String telefone, 
		
		@Email
		@NotBlank
		String email, 
		
		@NotNull
		@Valid
		DadosEndereco endereco) {
}
