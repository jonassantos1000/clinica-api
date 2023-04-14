package com.app.med.project.api.infra;

import org.springframework.validation.FieldError;

public record ErroDetalhe(String campo, String mensagem) {
	public ErroDetalhe(FieldError erro) {
		this(erro.getField(), erro.getDefaultMessage());
	}
}
