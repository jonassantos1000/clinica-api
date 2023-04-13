package com.app.med.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.med.project.api.domains.Medico;
import com.app.med.project.api.domains.dto.DadosCadastroMedico;
import com.app.med.project.api.domains.dto.ListagemResumoMedico;
import com.app.med.project.api.service.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	MedicoService service;
	
	@PostMapping
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico medicoDTO) {
		Medico medico = new Medico(medicoDTO);
		service.salvar(medico);
	}
	
	@GetMapping("/resumo")
	public Page<ListagemResumoMedico> listar(Pageable paginacao){
		return service.listagemResumidaMedico(paginacao);
	}
	
}
