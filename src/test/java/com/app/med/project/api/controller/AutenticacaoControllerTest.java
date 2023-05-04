package com.app.med.project.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.med.project.api.ControllerAbstrato;
import com.app.med.project.api.domains.usuario.DadosAutenticacao;
import com.app.med.project.api.infra.exception.RespostaErro;
import com.app.med.project.api.infra.security.DadosTokenJWT;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class AutenticacaoControllerTest extends ControllerAbstrato{

	private static final String URL_AUTENTICACAO= URL_BASE + "/auth";
	
	@Test
	@DisplayName("Deveria retornar erro ao inserir usuario invalido")
	void autenticarCenario1() {
		
		var dadosAutenticacao = new DadosAutenticacao("joao@teste.com", "senhainvalida");

		var respostaRequisicao = restTemplate.exchange(URI.create(URL_AUTENTICACAO), HttpMethod.POST,
				new HttpEntity<>(dadosAutenticacao, pegarHeader()), RespostaErro.class);

		assertEquals(HttpStatus.FORBIDDEN, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody() == null);
	}
	
	@Test
	@DisplayName("Deveria retornar o token de autenticação")
	void autenticarCenario2() {
		
		var dadosAutenticacao = new DadosAutenticacao("joao@clinicateste.com.br", "123456");

		var respostaRequisicao = restTemplate.exchange(URI.create(URL_AUTENTICACAO), HttpMethod.POST,
				new HttpEntity<>(dadosAutenticacao, pegarHeader()), DadosTokenJWT.class);

		assertEquals(HttpStatus.OK, respostaRequisicao.getStatusCode());
		assertTrue(respostaRequisicao.getBody().token() != null);
	}

}
