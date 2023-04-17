package com.app.med.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.med.project.api.domains.usuario.DadosAutenticacao;
import com.app.med.project.api.domains.usuario.Usuario;
import com.app.med.project.api.infra.security.DadosTokenJWT;
import com.app.med.project.api.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity autenticar(@RequestBody @Valid DadosAutenticacao dados) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		Authentication auth = manager.authenticate(authenticationToken);
		String tokenJWT = tokenService.gerarToken((Usuario) auth.getPrincipal());
		return ResponseEntity.ok().body(new DadosTokenJWT(tokenJWT));
	}
}
