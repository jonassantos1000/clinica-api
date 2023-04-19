package com.app.med.project.api.domains.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.med.project.api.domains.consulta.ConsultaRepository;
import com.app.med.project.api.domains.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {
    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        boolean medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new IllegalArgumentException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
