package com.app.med.project.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.med.project.api.domains.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

}
