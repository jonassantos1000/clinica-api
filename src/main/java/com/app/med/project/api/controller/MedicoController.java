package com.app.med.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.med.project.api.domains.Medico;
import com.app.med.project.api.domains.dto.DadosAtualizacaoMedico;
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

	@PutMapping
	public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico medicoDTO) {
		service.alterar(medicoDTO);
	}

	@GetMapping("/resumo")
	public Page<ListagemResumoMedico> listar(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return service.listagemResumidaMedico(paginacao);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Long id) {
		service.inativar(id);
	}

}
