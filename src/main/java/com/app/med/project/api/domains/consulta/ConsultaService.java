package com.app.med.project.api.domains.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.medico.Medico;
import com.app.med.project.api.domains.medico.MedicoService;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ConsultaService {

	@Autowired
	ConsultaRepository consultaRepository;

	@Autowired
	MedicoService medicoService;

	@Autowired
	PacienteService pacienteService;

	@Transactional
	public void agendar(DadosAgendamentoConsulta consultaDTO) {
		Medico medico = escolherMedico(consultaDTO);
		Paciente paciente = pacienteService.consultarPacientePorId(consultaDTO.idPaciente());

		Consulta consulta = new Consulta(null, medico, paciente, consultaDTO.data(), null);
		consultaRepository.save(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta consultaDTO) {
		// A regra de negocio, consiste que o id do medico pode vir nulo no DTO, nessa
		// situação o sistema deve atribuir aleariamente um medico na consulta.
		if (consultaDTO.idMedico() != null) {
			return medicoService.consultarMedicoPorId(consultaDTO.idMedico());
		}

		if (consultaDTO.especialidade() == null) {
			throw new IllegalArgumentException(
					"O campo especialidade é obrigatório para prosseguir com o agendamento dinamico de consultas.");
		}

		return medicoService.escolherMedicoAleatorioPorEspecialiade(consultaDTO.especialidade(), consultaDTO.data());
	}

	public void cancelar(@Valid DadosCancelamentoConsulta cancelamento) {
		if (!consultaRepository.existsById(cancelamento.idConsulta())) {
			throw new IllegalArgumentException("Consulta não encontrada!");
		}
		
		Consulta consulta = consultaRepository.getReferenceById(cancelamento.idConsulta());
		consulta.cancelar(cancelamento.motivo());
		consultaRepository.save(consulta);
	}

}
