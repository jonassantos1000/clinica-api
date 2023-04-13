package com.app.med.project.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.Medico;
import com.app.med.project.api.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	MedicoRepository repository;
	
	public void salvar(Medico medico) {
		repository.save(medico);
	}
}
