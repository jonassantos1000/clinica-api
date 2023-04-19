package com.app.med.project.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.app.med.project.api.domains.medico.DadosAtualizacaoMedico;
import com.app.med.project.api.domains.medico.DadosCadastroMedico;
import com.app.med.project.api.domains.medico.DadosDetalhamentoMedico;
import com.app.med.project.api.domains.medico.DadosResumidoMedico;
import com.app.med.project.api.domains.medico.Medico;
import com.app.med.project.api.domains.medico.MedicoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

	@Autowired
	MedicoService service;

	@PostMapping
	public ResponseEntity<DadosResumidoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico medicoDTO,
			UriComponentsBuilder uBuilder) {
		Medico medico = new Medico(medicoDTO);
		service.salvar(medico);

		URI uri = uBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosResumidoMedico(medico));
	}

	@PutMapping
	public ResponseEntity<Void> atualizar(@RequestBody @Valid DadosAtualizacaoMedico medicoDTO) {
		service.alterar(medicoDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	public ResponseEntity<Page<DadosResumidoMedico>> listar(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return ResponseEntity.ok().body(service.consultarListagemResumidaMedico(paginacao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoMedico> listar(@PathVariable Long id) {
		return ResponseEntity.ok().body(new DadosDetalhamentoMedico(service.consultarMedicoPorId(id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.inativar(id);
		return ResponseEntity.noContent().build();
	}

}
