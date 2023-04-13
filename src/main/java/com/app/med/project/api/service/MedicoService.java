package com.app.med.project.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.Medico;
import com.app.med.project.api.domains.dto.ListagemResumoMedico;
import com.app.med.project.api.repository.MedicoRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicoService {

	@Autowired
	MedicoRepository repository;
	
	@Transactional
	public void salvar(Medico medico) {
		repository.save(medico);
	}

	public Page<ListagemResumoMedico> listagemResumidaMedico(Pageable paginacao) {
		return repository.findAll(paginacao).map(ListagemResumoMedico::new);
	}
}
