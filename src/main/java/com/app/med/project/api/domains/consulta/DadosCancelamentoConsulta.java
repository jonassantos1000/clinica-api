package com.app.med.project.api.domains.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(

		@NotNull Long id,

		@NotNull(message = "O motivo deve ser estar preenchido para realizar o cancelamento da consulta.") 
		MotivoCancelamento motivo

) {

}
