package com.app.med.project.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.Medico;
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
}
