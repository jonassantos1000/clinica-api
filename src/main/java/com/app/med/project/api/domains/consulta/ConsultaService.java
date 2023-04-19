package com.app.med.project.api.domains.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import com.app.med.project.api.domains.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import com.app.med.project.api.domains.medico.Medico;
import com.app.med.project.api.domains.medico.MedicoService;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadoresAgendamento;

	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

	@Transactional
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta consultaDTO) {
		validadoresAgendamento.forEach(validador -> validador.validar(consultaDTO));

		Medico medico = escolherMedico(consultaDTO);
		Paciente paciente = pacienteService.consultarPacientePorId(consultaDTO.idPaciente());

		Consulta consulta = new Consulta(null, medico, paciente, consultaDTO.data(), null);
		consultaRepository.save(consulta);
		return new DadosDetalhamentoConsulta(consulta);
	}

	public void cancelar(@Valid DadosCancelamentoConsulta cancelamento) {
		consultarAgendamentoPorId(cancelamento.id());
		validadoresCancelamento.forEach(validador -> validador.validar(cancelamento));

		Consulta consulta = consultaRepository.getReferenceById(cancelamento.id());
		consulta.cancelar(cancelamento.motivo());
		consultaRepository.save(consulta);
	}

	public Consulta consultarAgendamentoPorId(Long id) {
		return consultaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				"Id da consulta informado não existe. Verifique os parametros de busca e tente novamente!"));
	}
	
	public List<DadosDetalhamentoConsulta> consultarListagemAgendamento(Pageable paginacao) {
		return consultaRepository.findAllByMotivoIsNull(paginacao).stream().map(DadosDetalhamentoConsulta::new).toList();
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

}
