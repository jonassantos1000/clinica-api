package com.app.med.project.api.domains.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.endereco.Endereco;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PacienteService {

	@Autowired
	PacienteRepository repository;

	@Transactional
	public void salvar(Paciente paciente) {
		repository.save(paciente);
	}

	public Paciente consultarPacientePorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Id do paciente informado não existe. Verifique os parametros de busca e tente novamente!"));
	}

	public Page<DadosDetalhamentoPaciente> consultarListagemPaciente(Pageable paginacao) {
		return repository.findAll(paginacao).map(DadosDetalhamentoPaciente::new);
	}

	public void alterar(DadosAtualizacaoPaciente dadosAtualizados) {
		Paciente paciente = consultarPacientePorId(dadosAtualizados.id());
		atualizarDados(paciente, dadosAtualizados);
		repository.save(paciente);
	}

	public void remover(Long id) {
		Paciente paciente = consultarPacientePorId(id);
		repository.delete(paciente);
	}

	private void atualizarDados(Paciente dadosAtuais, DadosAtualizacaoPaciente dadosAtualizados) {
		if (!dadosAtualizados.email().equals(null))
			dadosAtuais.setEndereco(new Endereco(dadosAtualizados.endereco()));
		
		if (!dadosAtualizados.telefone().isBlank())
			dadosAtuais.setTelefone(dadosAtualizados.telefone());
		
		if (!dadosAtualizados.email().isBlank())
			dadosAtuais.setEmail(dadosAtualizados.email());
	}
}
