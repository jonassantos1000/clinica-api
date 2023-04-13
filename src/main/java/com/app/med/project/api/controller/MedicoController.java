package com.app.med.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.med.project.api.domains.Medico;
import com.app.med.project.api.domains.dto.DadosCadastraisMedico;
import com.app.med.project.api.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	MedicoService service;
	
	@PostMapping
	public void cadastrar(@RequestBody DadosCadastraisMedico medicoDTO) {
		Medico medico = new Medico(medicoDTO);
		service.salvar(medico);
	}

}
