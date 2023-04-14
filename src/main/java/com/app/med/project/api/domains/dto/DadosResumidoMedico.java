package com.app.med.project.api.domains.dto;

import com.app.med.project.api.domains.Especialidade;
import com.app.med.project.api.domains.Medico;

public record DadosResumidoMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

	public DadosResumidoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
}
