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
	
	public Paciente buscarPacientePorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Verifique os parametros de busca e tente novamente!"));
	}

	public Page<DadosDetalhamentoPaciente> consultarListagemPaciente(Pageable paginacao) {
		return repository.findAll(paginacao).map(DadosDetalhamentoPaciente::new);
	}
	
	public void alterar(DadosAtualizacaoPaciente dadosAtualizados) {
		Paciente paciente = buscarPacientePorId(dadosAtualizados.id());
		atualizarDados(paciente, dadosAtualizados);
		repository.save(paciente);
	}
	
	public void remover(Long id) {
		Paciente paciente = buscarPacientePorId(id);
		repository.delete(paciente);
	}
	
	private void atualizarDados(Paciente dadosAtuais, DadosAtualizacaoPaciente dadosAtualizados) {
		dadosAtuais.setEndereco(new Endereco(dadosAtualizados.endereco()));
		dadosAtuais.setTelefone(dadosAtualizados.telefone());
	}
}
