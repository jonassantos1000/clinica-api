package com.app.med.project.api.domains.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.app.med.project.api.domains.consulta.Consulta;
import com.app.med.project.api.domains.consulta.ConsultaService;
import com.app.med.project.api.domains.consulta.DadosCancelamentoConsulta;


@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

	@Autowired
	@Lazy
	ConsultaService consultaService;
	
	// O agendamento deve ter pelo menos 30 minutos de Antecedencia
	public void validar(DadosCancelamentoConsulta dados) {
		Consulta consulta = consultaService.consultarAgendamentoPorId(dados.id());

		LocalDateTime dataConsulta = consulta.getData();
		LocalDateTime dataAtual = LocalDateTime.now();

		Long diferencaHorarioEmHoras = Duration.between(dataAtual, dataConsulta).toHours();

		if (diferencaHorarioEmHoras < 24) {
			throw new IllegalArgumentException(
					"A consulta só pode ser cancelada com antedência mínima de 24 horas");
		}
	}

}
