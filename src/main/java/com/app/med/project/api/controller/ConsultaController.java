package com.app.med.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.med.project.api.domains.consulta.ConsultaService;
import com.app.med.project.api.domains.consulta.DadosAgendamentoConsulta;
import com.app.med.project.api.domains.consulta.DadosCancelamentoConsulta;

import jakarta.validation.Valid;



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
	
	@DeleteMapping
	public ResponseEntity<Void> cancelar(@RequestBody @Valid DadosCancelamentoConsulta cancelamento){
		service.cancelar(cancelamento);
		return ResponseEntity.noContent().build();
	}
}