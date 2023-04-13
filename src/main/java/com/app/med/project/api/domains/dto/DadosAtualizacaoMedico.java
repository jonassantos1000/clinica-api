package com.app.med.project.api.domains.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
		@NotNull
		Long id, String telefone, DadosEndereco endereco) {

}
