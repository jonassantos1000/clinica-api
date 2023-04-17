package com.app.med.project.api.domains.consulta.validacoes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.app.med.project.api.domains.consulta.DadosAgendamentoConsulta;

public class ValidadorHorarioFuncionamentoClinica {
	
	//A clinica funciona de segunda à sabado, das 07:00 as 19:00
	public void validar(DadosAgendamentoConsulta dados) {
		LocalDateTime dataConsulta = dados.data();
		boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		boolean antesAberturaDaClinica = dataConsulta.getHour() < 7;
		boolean depoisEncerramentoDaClinica = dataConsulta.getHour() > 18;
		
		if (domingo || antesAberturaDaClinica || depoisEncerramentoDaClinica) {
			throw new IllegalArgumentException("Consulta fora do horário de funcionamento da clinica");
		}
	}
}
