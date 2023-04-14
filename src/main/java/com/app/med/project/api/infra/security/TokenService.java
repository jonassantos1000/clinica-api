package com.app.med.project.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.med.project.api.domains.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarToken(Usuario usuario) {
		try {
		    Algorithm algoritmo = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("api med")
		        .withSubject(usuario.getLogin())
		        .withExpiresAt(dataExpiracao())
		        .withClaim("id", usuario.getId())
		        .sign(algoritmo);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar token jwt");
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
