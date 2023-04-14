package com.app.med.project.api.infra;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GerenciadorDeErros {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<RespostaErro> tratarErro404(EntityNotFoundException e, HttpServletRequest request){
		RespostaErro erro = new RespostaErro(Instant.now(), "Recurso não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

}
