package com.app.med.project.api.infra.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
	
    @ExceptionHandler(CredencialInvalida.class)
    public ResponseEntity<RespostaErro> tratarErroBadCredentials(CredencialInvalida e, HttpServletRequest request) {
		RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Crendenciais invalidas!", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespostaErro> tratarErroIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
		RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Argumento Invalido", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RespostaErro> tratarErroSQL(DataIntegrityViolationException e, HttpServletRequest request) {
    	RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Não foi possivel finalizar a operação, pois ela infringe regras de integridade", e.getMostSpecificCause().getMessage()), request.getRequestURI());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RespostaErro> tratarErroAcessoNegado(AccessDeniedException e, HttpServletRequest request) {
		RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Acesso negado", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }
 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> tratarErro500(Exception e, HttpServletRequest request) {
    	RespostaErro erro = new RespostaErro(Instant.now(), new ErroDetalhe("Ocorreu um erro durante o processamento da requisição", e.getMessage()), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
