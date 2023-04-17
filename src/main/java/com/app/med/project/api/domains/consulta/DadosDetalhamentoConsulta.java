package com.app.med.project.api.domains.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long idConsulta, Long idMedico, Long idPaciente, LocalDateTime data) {

	public DadosDetalhamentoConsulta(Consulta consulta) {
		this(consulta.getIdConsulta(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
	}
}
