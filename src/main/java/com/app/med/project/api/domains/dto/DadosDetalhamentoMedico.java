package com.app.med.project.api.domains.dto;

import com.app.med.project.api.domains.Endereco;
import com.app.med.project.api.domains.Especialidade;
import com.app.med.project.api.domains.Medico;

public record DadosDetalhamentoMedico(Long id, String nome, String crm, String email, String telefone,
		Especialidade especialidade, Endereco endereco) {

	public DadosDetalhamentoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getCrm(), medico.getEmail(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
	}

}
