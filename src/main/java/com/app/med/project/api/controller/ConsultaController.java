package com.app.med.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.med.project.api.domains.consulta.ConsultaService;
import com.app.med.project.api.domains.consulta.DadosAgendamentoConsulta;



@RestController
@RequestMapping("/agendamentos")
public class ConsultaController {
	
	@Autowired
	ConsultaService service;
	
	@PostMapping
	public ResponseEntity<DadosAgendamentoConsulta> agendar(DadosAgendamentoConsulta agendamentoDTO){
		service.agendar(agendamentoDTO);
		
		return null;
		
	}
}
