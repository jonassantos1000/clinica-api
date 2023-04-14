package com.app.med.project.api.infra;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record RespostaErro(
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="GMT")
		Instant timestamp, List<ErroDetalhe> erros, String path) {
	
	public RespostaErro(Instant timestamp, ErroDetalhe erros, String path) {
		this(timestamp, Arrays.asList(erros), path);
	}

}
