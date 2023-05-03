package com.app.med.project.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.app.med.project.api.domains.usuario.DadosAutenticacao;
import com.app.med.project.api.infra.security.DadosTokenJWT;

public class ControllerAbstrato implements BaseController {
	
	private static final String URL_AUTENTICACAO = URL_BASE + "/auth";
			
	@Autowired
	protected TestRestTemplate restTemplate;
	
	protected String pegarToken(){
		var credenciais = new DadosAutenticacao("joao@clinicateste.com.br", "123456"); 
		var respostaRequisicao = restTemplate.postForEntity(URI.create(URL_AUTENTICACAO), credenciais, DadosTokenJWT.class);
		return respostaRequisicao.getBody().token();
	}
}
