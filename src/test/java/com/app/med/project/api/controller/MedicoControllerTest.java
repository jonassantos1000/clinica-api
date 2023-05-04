package com.app.med.project.api.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.med.project.api.ControllerAbstrato;
import com.app.med.project.api.domains.endereco.Endereco;
import com.app.med.project.api.domains.medico.DadosDetalhamentoMedico;
import com.app.med.project.api.domains.medico.Especialidade;
import com.app.med.project.api.domains.medico.Medico;
import com.app.med.project.api.domains.medico.MedicoService;
import com.app.med.project.api.infra.exception.RespostaErro;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class MedicoControllerTest extends ControllerAbstrato{

	private static final String URL_MEDICO = URL_BASE + "/medicos";
	private Medico medico;

	@Autowired
	MedicoService medicoService;
	

	@Test
	@DisplayName("Deveria cadastrar medico valido")
	void cadastrarMedicoCenario1() {
		var medico = criarMedico();

		var respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO), HttpMethod.POST,
				new HttpEntity<Medico>(medico, pegarHeader()), DadosDetalhamentoMedico.class);

		assertEquals(HttpStatus.CREATED, respostaRequisicao.getStatusCode());
		assertEquals(medico.getNome(), respostaRequisicao.getBody().nome());
	}

	@Test
	@DisplayName("Não deve cadastrar medico com nome em branco")
	void cadastrarMedicoCenario2() {
		var medico = criarMedico();
		medico.setNome("");

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO),
				HttpMethod.POST, new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar medico com telefone em branco")
	void cadastrarMedicoCenario3() {
		var medico = criarMedico();
		medico.setTelefone("");

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO),
				HttpMethod.POST, new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar medico com email em branco")
	void cadastrarMedicoCenario4() {
		var medico = criarMedico();
		medico.setEmail("");

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO),
				HttpMethod.POST, new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar medico com email null")
	void cadastrarMedicoCenario5() {
		var medico = criarMedico();
		medico.setEmail(null);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO),
				HttpMethod.POST, new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve cadastrar medico com endereco null")
	void cadastrarMedicoCenario6() {
		var medico = criarMedico();
		medico.setEndereco(null);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO),
				HttpMethod.POST, new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.BAD_REQUEST, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve retornar um Medico com id invalido")
	void consultarMedicoCenario1() {
		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO + "/0"),
				HttpMethod.GET, new HttpEntity<>("body", pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NOT_FOUND, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Deve consultar um Medico com id valido")
	void consultarMedicoCenario2() {
		Medico medico = this.medico;

		ResponseEntity<DadosDetalhamentoMedico> respostaRequisicao = restTemplate.exchange(
				URI.create(URL_MEDICO + "/" + medico.getId()), HttpMethod.GET,
				new HttpEntity<>("body", pegarHeader()), DadosDetalhamentoMedico.class);

		assertEquals(HttpStatus.OK, respostaRequisicao.getStatusCode());
		assertEquals(medico.getNome(), respostaRequisicao.getBody().nome());
		assertEquals(medico.getTelefone(), respostaRequisicao.getBody().telefone());
	}

	@Test
	@DisplayName("Deve alterar o medico existente")
	void alterarMedicoCenario1() {
		Medico medico = this.medico;
		alterarMedico(medico);

		ResponseEntity<DadosDetalhamentoMedico> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO),
				HttpMethod.PUT, new HttpEntity<Medico>(medico, pegarHeader()), DadosDetalhamentoMedico.class);

		assertEquals(HttpStatus.OK, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody() == null);
	}

	@Test
	@DisplayName("Não deve alterar o medico inexistente")
	void alterarMedicoCenario2() {
		Medico medico = criarMedico();
		medico.setId(0L);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(URI.create(URL_MEDICO),
				HttpMethod.PUT, new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NOT_FOUND, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Não deve deletar o medico inexistente")
	void deletarMedicoCenario1() {
		Medico medico = criarMedico();
		medico.setId(0L);

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(
				URI.create(URL_MEDICO + "/" + medico.getId()), HttpMethod.DELETE,
				new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NOT_FOUND, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().erros().size() > 0);
	}

	@Test
	@DisplayName("Deve deletar o medico valido através do id")
	void deletarMedicoCenario2() {
		Medico medico2 = inserirMedicoPadrao();

		ResponseEntity<RespostaErro> respostaRequisicao = restTemplate.exchange(
				URI.create(URL_MEDICO + "/" + medico2.getId()), HttpMethod.DELETE,
				new HttpEntity<Medico>(medico, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.NO_CONTENT, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody() == null);
	}
	
	@BeforeEach
	void setup() {
		this.medico = inserirMedicoPadrao();
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

	private Medico criarMedico() {
		var valorAleatorio = geradorDeCodigoAleatorio();
		var crm = valorAleatorio;
		var incrementoEmail=valorAleatorio;
		var medico = new Medico(null, "medico teste", "medicoteste"+incrementoEmail+"@testando.com", crm, "1154515232", true, Especialidade.CARDIOLOGIA, dadosEndereco());
		return medico;
	}

	private Medico inserirMedicoPadrao() {
		Medico medico = criarMedico();
		medicoService.salvar(medico);
		return medico;
	}

	private void alterarMedico(Medico medico) {
		medico.setEmail("testealteracao@gmail.com");
		medico.setNome("testealteracao");
	}
	
	private String geradorDeCodigoAleatorio() {
		Random geradorNumeros = new Random();
		return String.format("%06d", geradorNumeros.nextInt(1000));
	}

}
