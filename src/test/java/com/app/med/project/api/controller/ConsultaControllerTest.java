package com.app.med.project.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.med.project.api.ControllerAbstrato;
import com.app.med.project.api.domains.consulta.ConsultaService;
import com.app.med.project.api.domains.consulta.DadosAgendamentoConsulta;
import com.app.med.project.api.domains.consulta.DadosDetalhamentoConsulta;
import com.app.med.project.api.domains.endereco.Endereco;
import com.app.med.project.api.domains.medico.Especialidade;
import com.app.med.project.api.domains.medico.Medico;
import com.app.med.project.api.domains.medico.MedicoService;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class ConsultaControllerTest extends ControllerAbstrato {

	private static final String URL_CONSULTA = URL_BASE + "/agendamentos";
	
	@Autowired
	private ConsultaService consultaService;

	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private MedicoService medicoService;
	
	private Paciente paciente;
	
	private Medico medico;
	

	@Test
	@DisplayName("Deveria agendar uma consulta com dados validos")
	void agendarConsultaCenario1() {
		DadosAgendamentoConsulta consulta = gerarAgendamento();

		var respostaRequisicao = restTemplate.exchange(URI.create(URL_CONSULTA), HttpMethod.POST,
				new HttpEntity<DadosAgendamentoConsulta>(consulta, pegarHeader()), DadosDetalhamentoConsulta.class);
		
		assertEquals(HttpStatus.OK, respostaRequisicao.getStatusCode());
		assertEquals(consulta.idMedico(), respostaRequisicao.getBody().idMedico());
		assertEquals(consulta.idPaciente(), respostaRequisicao.getBody().idPaciente());
	}
	
	
	@BeforeEach
	void setup() {
		inserirMedicoPadrao();
		inserirPacientePadrao();
	}
	
	private DadosAgendamentoConsulta gerarAgendamento() {		
		var dataComAntecedencia = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).atTime(10, 0);
		return new DadosAgendamentoConsulta(medico.getId(), paciente.getId(), dataComAntecedencia, Especialidade.CARDIOLOGIA);
	}

	private Paciente inserirPacientePadrao() {
		Paciente paciente = new Paciente(null, "paciente", dadosEndereco(), "paciente@terra.com", "123456789");
		pacienteService.salvar(paciente);
		this.paciente = paciente;
		
		return paciente;
	}
	
	private Medico inserirMedicoPadrao() {
		var valorAleatorio = geradorDeCodigoAleatorio();
		var crm = valorAleatorio;
		var incrementoEmail=valorAleatorio;
		var medico = new Medico(null, "medico teste", "medicoteste"+incrementoEmail+"@testando.com", crm, "1154515232", true, Especialidade.CARDIOLOGIA, dadosEndereco());

		medicoService.salvar(medico);
		this.medico = medico;
		
		return medico;
	}
	
	private String geradorDeCodigoAleatorio() {
		Random geradorNumeros = new Random();
		return String.format("%06d", geradorNumeros.nextInt(1000));
	}
	
	private Endereco dadosEndereco() {
		var endereco = new Endereco();
		endereco.setBairro("teste");
		endereco.setCep("12354844");
		endereco.setCidade("SP");
		endereco.setComplemento("teste");
		endereco.setLogradouro("teste");
		endereco.setNumero("1");
		endereco.setUf("sp");
		return endereco;
	}
	

}
