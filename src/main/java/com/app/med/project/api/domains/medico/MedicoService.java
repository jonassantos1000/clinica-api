package com.app.med.project.api.domains.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.endereco.Endereco;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MedicoService {

	@Autowired
	MedicoRepository repository;
	
	@Transactional
	public void salvar(Medico medico) {
		repository.save(medico);
	}
	
	public Medico buscarMedicoPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Verifique os parametros de busca e tente novamente!"));
	}

	public Page<DadosResumidoMedico> listagemResumidaMedico(Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao).map(DadosResumidoMedico::new);
	}
	
	public void alterar(DadosAtualizacaoMedico dadosAtualizados) {
		Medico medico = buscarMedicoPorId(dadosAtualizados.id());
		atualizarDados(medico, dadosAtualizados);
		repository.save(medico);
	}
	
	public void inativar(Long id) {
		Medico medico = buscarMedicoPorId(id);
		medico.setAtivo(false);
		repository.save(medico);
	}
	
	private void atualizarDados(Medico dadosAtuais, DadosAtualizacaoMedico dadosAtualizados) {
		dadosAtuais.setEndereco(new Endereco(dadosAtualizados.endereco()));
		dadosAtuais.setTelefone(dadosAtualizados.telefone());
	}
}