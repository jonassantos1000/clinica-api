package com.app.med.project.api.domains.dto;

import com.app.med.project.api.domains.Especialidade;

public record DadosCadastroMedico(String nome, String crm, String email,Especialidade especialidade, DadosEndereco endereco) {

}
