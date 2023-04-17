package com.app.med.project.api.domains.medico;

import com.app.med.project.api.domains.endereco.DadosEndereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
		@NotNull
		Long id,

		@NotBlank
		String telefone,

		@NotNull
		DadosEndereco endereco) {

}
