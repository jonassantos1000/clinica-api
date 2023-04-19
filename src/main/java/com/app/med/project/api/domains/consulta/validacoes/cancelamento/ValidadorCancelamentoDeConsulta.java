package com.app.med.project.api.domains.consulta.validacoes.cancelamento;

import com.app.med.project.api.domains.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
	void validar(DadosCancelamentoConsulta dados);
}
