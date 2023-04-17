package com.app.med.project.api.domains.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.app.med.project.api.domains.medico.Medico;
import com.app.med.project.api.domains.medico.MedicoService;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;

import jakarta.transaction.Transactional;

@Service
public class ConsultaService {

	@Autowired
	ConsultaService consultaRepository;

	@Autowired
	MedicoService medicoService;

	@Autowired
	PacienteService pacienteService;

	@Transactional
	public void agendar(DadosAgendamentoConsulta consultaDTO) {
		// A regra de negocio, consiste que o id do medico pode vir nulo no DTO, nessa
		// situação o sistema deve atribuir aleariamente um medico na consulta.
		Medico medico = escolherMedico(consultaDTO);
		Paciente paciente = pacienteService.consultarPacientePorId(consultaDTO.idPaciente());

		Consulta consulta = new Consulta(null, medico, paciente, consultaDTO.data());
	}

	private Medico escolherMedico(DadosAgendamentoConsulta consultaDTO) {
		if (consultaDTO.idMedico() != null) {
			return medicoService.consultarMedicoPorId(consultaDTO.idMedico());
		}
		
		if (consultaDTO.especialidade() == null) {
			throw new IllegalArgumentException("O campo especialidade é obrigatório para prosseguir com o agendamento dinamico de consultas.");
		}

		return medicoService.escolherMedicoAleatorioPorEspecialiade(consultaDTO.especialidade(), consultaDTO.data());
	}

}
