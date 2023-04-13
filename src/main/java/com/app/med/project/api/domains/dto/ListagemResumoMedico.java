package com.app.med.project.api.domains.dto;

import com.app.med.project.api.domains.Especialidade;
import com.app.med.project.api.domains.Medico;

public record ListagemResumoMedico(String nome, String email, String crm, Especialidade especialidade) {

	public ListagemResumoMedico(Medico medico) {
		this(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
}
