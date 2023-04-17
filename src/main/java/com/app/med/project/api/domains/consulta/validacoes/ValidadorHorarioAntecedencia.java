package com.app.med.project.api.domains.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.app.med.project.api.domains.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{

	//O agendamento deve ter pelo menos 30 minutos de Antecedencia
	public void validar(DadosAgendamentoConsulta dados) {
		LocalDateTime dataConsulta = dados.data();
		LocalDateTime dataAtual = LocalDateTime.now();
		
		Long diferencaHorarioEmMinutos = Duration.between(dataConsulta, dataAtual).toMinutes(); 
		
		if(diferencaHorarioEmMinutos < 30) {
			throw new IllegalArgumentException("A consulta deve ser agendada com antecedencia minima de 30 minutos");
		}
	}
}
