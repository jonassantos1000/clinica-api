package com.app.med.project.api.domains.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long idConsulta, Long idMedico, Long idPaciente, LocalDateTime data) {
}
