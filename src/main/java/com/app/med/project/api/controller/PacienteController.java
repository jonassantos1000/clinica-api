package com.app.med.project.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.med.project.api.domains.paciente.DadosCadastroPaciente;
import com.app.med.project.api.domains.paciente.DadosDetalhamentoPaciente;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	PacienteService service;
	
	@PostMapping
	public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody DadosCadastroPaciente pacienteDTO, UriComponentsBuilder uBuilder){
		Paciente paciente = new Paciente(pacienteDTO);
		service.salvar(paciente);
		
		URI uri = uBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
	}

}
