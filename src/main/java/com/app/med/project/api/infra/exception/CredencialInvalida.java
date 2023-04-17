package com.app.med.project.api.infra.exception;

public class CredencialInvalida extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CredencialInvalida(String message) {
		super(message);
	}

}
