package com.app.med.project.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<ListagemResumoMedico> listagemResumidaMedico() {
		return repository.findAll().stream().map(ListagemResumoMedico::new).toList();
	}
}
