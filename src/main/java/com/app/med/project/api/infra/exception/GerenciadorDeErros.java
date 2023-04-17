package com.app.med.project.api.infra.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GerenciadorDeErros {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<RespostaErro> tratarErro404(EntityNotFoundException e, HttpServletRequest request){
		RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Recurso não encontrado", e.getMessage()), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RespostaErro> tratarErro400(MethodArgumentNotValidException e, HttpServletRequest request){
		List<ErroDetalhe> errosValidacao = e.getFieldErrors().stream().map(ErroDetalhe::new).toList();
		RespostaErro erro = new RespostaErro(Instant.now(), errosValidacao, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RespostaErro> tratarErroBadCredentials(BadCredentialsException e, HttpServletRequest request) {
		RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Crendenciais invalidas!", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<RespostaErro> tratarErroAuthentication(AuthenticationCredentialsNotFoundException e, HttpServletRequest request) {
		RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Falha na autenticação", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RespostaErro> tratarErroAcessoNegado(AccessDeniedException e, HttpServletRequest request) {
		RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Acesso negado", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> tratarErro500(Exception e, HttpServletRequest request) {
    	RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Acesso negado", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
