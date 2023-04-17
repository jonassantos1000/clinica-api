package com.app.med.project.api.domains.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(

		@NotBlank Long idConsulta,

		@NotNull MotivoCancelamento motivo

) {

}
