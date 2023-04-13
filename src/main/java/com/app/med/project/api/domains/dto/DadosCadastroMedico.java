package com.app.med.project.api.domains.dto;

import com.app.med.project.api.domains.Especialidade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
		@NotBlank
		String nome,

		@NotBlank
		@Pattern(regexp= "\\d{4,6}")
		String crm,

		@Email
		@NotBlank
		String email,

		@NotBlank
		String telefone,

		@NotNull
		Especialidade especialidade,

		@NotNull
		@Valid
		DadosEndereco endereco)

{}
