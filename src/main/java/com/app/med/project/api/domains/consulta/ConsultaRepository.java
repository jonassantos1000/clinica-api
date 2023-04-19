package com.app.med.project.api.domains.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
	Page<Consulta> findAllByMotivoIsNull(Pageable paginacao);
}
