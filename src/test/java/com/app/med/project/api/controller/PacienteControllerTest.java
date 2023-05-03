package com.app.med.project.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.med.project.api.ControllerAbstrato;
import com.app.med.project.api.domains.endereco.Endereco;
import com.app.med.project.api.domains.paciente.DadosDetalhamentoPaciente;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class PacienteControllerTest extends ControllerAbstrato{

	private static final String URL_PACIENTE = URL_BASE + "/pacientes";

	@Autowired
	PacienteService pacienteService;
	
	@Test
	@DisplayName("Deveria cadastrar paciente valido")
	void cadastrarPacienteCenario1() {
		var paciente = criarPaciente();
	    HttpHeaders header = new HttpHeaders();
	    header.add("Authorization", "Bearer " + pegarToken());
		var respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE), HttpMethod.POST, new HttpEntity<Paciente>(paciente,header) , DadosDetalhamentoPaciente.class);
		
		assertEquals(HttpStatus.CREATED, respostaRequisicao.getStatusCode());
		assertEquals(paciente.getNome(), respostaRequisicao.getBody().nome());
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

	private Paciente criarPaciente() {
		var paciente = new Paciente(null, "paciente", dadosEndereco(), "paciente@terra.com", "123456789");
		return paciente;
	}
	
}
