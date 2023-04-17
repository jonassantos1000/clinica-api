package com.app.med.project.api.domains.consulta;

import java.time.LocalDateTime;

import com.app.med.project.api.domains.medico.Especialidade;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(Long idMedico,

		@NotNull Long idPaciente,

		@NotNull @Future LocalDateTime data,

		Especialidade especialidade) {

}
