package com.app.med.project.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityFilter extends OncePerRequestFilter {

	@Value("${api.security.token.secret}")
	private String secret;
	
	@Autowired
	TokenService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String tokenJWT = recuperarToken(request);
		String subject = service.getSubject(tokenJWT);
		
		filterChain.doFilter(request, response);
		
	}
	
	private String recuperarToken(HttpServletRequest request) {
		String authentication = request.getHeader("Authorization"); 
		
		if (authentication == null) {
			throw new RuntimeException("Token JWT não foi enviado!");
		}
		
		return authentication.replace("Bearer ", "");
	}

}
