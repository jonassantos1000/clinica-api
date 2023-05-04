package com.app.med.project.api.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.med.project.api.ControllerAbstrato;
import com.app.med.project.api.domains.endereco.Endereco;
import com.app.med.project.api.domains.paciente.DadosDetalhamentoPaciente;
import com.app.med.project.api.domains.paciente.Paciente;
import com.app.med.project.api.domains.paciente.PacienteService;
import com.app.med.project.api.infra.exception.RespostaErro;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class PacienteControllerTest extends ControllerAbstrato {

	private static final String URL_PACIENTE = URL_BASE + "/pacientes";
	private Paciente paciente;

	@Autowired
	PacienteService pacienteService;
	

	@Test
	@DisplayName("Deveria cadastrar paciente valido")
	void cadastrarPacienteCenario1() {
		var paciente = criarPaciente();

		var respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE), HttpMethod.POST,
				new HttpEntity<Paciente>(paciente, pegarHeader()), DadosDetalhamentoPaciente.class);

		assertEquals(HttpStatus.CREATED, respostaRequisicao.getStatusCode());
		assertEquals(paciente.getNome(), respostaRequisicao.getBody().nome());
	}

	@Test
	@DisplayName("Não deve cadastrar paciente com nome em branco")
	void cadastrarPacienteCenario2() {
		var paciente = criarPaciente();
		paciente.setNome("");

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE),
				HttpMethod.POST, new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar paciente com telefone em branco")
	void cadastrarPacienteCenario3() {
		var paciente = criarPaciente();
		paciente.setTelefone("");

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE),
				HttpMethod.POST, new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar paciente com email em branco")
	void cadastrarPacienteCenario4() {
		var paciente = criarPaciente();
		paciente.setEmail("");

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE),
				HttpMethod.POST, new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar paciente com email null")
	void cadastrarPacienteCenario5() {
		var paciente = criarPaciente();
		paciente.setEmail(null);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE),
				HttpMethod.POST, new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar paciente com endereco null")
	void cadastrarPacienteCenario6() {
		var paciente = criarPaciente();
		paciente.setEndereco(null);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE),
				HttpMethod.POST, new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve retornar um Paciente com id invalido")
	void consultarPacienteCenario1() {
		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE + "/0"),
				HttpMethod.GET, new HttpEntity<>("body", pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NOT_FOUND, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Deve consultar um Paciente com id valido")
	void consultarPacienteCenario2() {
		Paciente paciente = this.paciente;

		ResponseEntity<DadosDetalhamentoPaciente> respostaRequisicao = restTemplate.exchange(
				URI.create(URL_PACIENTE + "/" + paciente.getId()), HttpMethod.GET,
				new HttpEntity<>("body", pegarHeader()), DadosDetalhamentoPaciente.class);

		assertEquals(HttpStatus.OK, respostaRequisicao.getStatusCode());
		assertEquals(paciente.getNome(), respostaRequisicao.getBody().nome());
		assertEquals(paciente.getTelefone(), respostaRequisicao.getBody().telefone());
	}

	@Test
	@DisplayName("Deve alterar o paciente existente")
	void alterarPacienteCenario1() {
		Paciente paciente = this.paciente;
		alterarPaciente(paciente);

		ResponseEntity<DadosDetalhamentoPaciente> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE),
				HttpMethod.PUT, new HttpEntity<Paciente>(paciente, pegarHeader()), DadosDetalhamentoPaciente.class);

		assertEquals(HttpStatus.OK, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody() == null);
	}

	@Test
	@DisplayName("Não deve alterar o paciente inexistente")
	void alterarPacienteCenario2() {
		Paciente paciente = criarPaciente();
		paciente.setId(0L);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_PACIENTE),
				HttpMethod.PUT, new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NOT_FOUND, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve deletar o paciente inexistente")
	void deletarPacienteCenario1() {
		Paciente paciente = criarPaciente();
		paciente.setId(0L);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(
				URI.create(URL_PACIENTE + "/" + paciente.getId()), HttpMethod.DELETE,
				new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NOT_FOUND, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Deve deletar o paciente valido através do id")
	void deletarPacienteCenario2() {
		Paciente paciente2 = inserirPacientePadrao();

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(
				URI.create(URL_PACIENTE + "/" + paciente2.getId()), HttpMethod.DELETE,
				new HttpEntity<Paciente>(paciente, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NO_CONTENT, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody() == null);
	}
	
	@BeforeEach
	void setup() {
		this.paciente = inserirPacientePadrao();
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

	private Paciente inserirPacientePadrao() {
		Paciente paciente = criarPaciente();
		pacienteService.salvar(paciente);
		return paciente;
	}

	private void alterarPaciente(Paciente paciente) {
		paciente.setEmail("testealteracao@gmail.com");
		paciente.setNome("testealteracao");
	}

}
