package com.app.med.project.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.med.project.api.domains.paciente.DadosAtualizacaoPaciente;
import com.app.med.project.api.domains.paciente.DadosCadastroPaciente;
import com.app.med.project.api.domains.paciente.DadosDetalhamentoPaciente;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	PacienteService service;

	@PostMapping
	public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente pacienteDTO,
			UriComponentsBuilder uBuilder) {
		Paciente paciente = new Paciente(pacienteDTO);
		service.salvar(paciente);

		URI uri = uBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
	}

	@PutMapping
	public ResponseEntity<Void> alterar(@RequestBody DadosAtualizacaoPaciente dadosAtualizacaoDTO) {
		service.alterar(dadosAtualizacaoDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Page<DadosDetalhamentoPaciente>> listar(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return ResponseEntity.ok().body(service.consultarListagemPaciente(paginacao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoPaciente> listar(@PathVariable Long id) {
		return ResponseEntity.ok().body(new DadosDetalhamentoPaciente(service.consultarPacientePorId(id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return ResponseEntity.noContent().build();
	}

}
