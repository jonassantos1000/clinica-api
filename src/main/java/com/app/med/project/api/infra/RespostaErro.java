package com.app.med.project.api.infra;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public record RespostaErro(
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="GMT")
		Instant timestamp, String causa, String mensagem, String path) {

}
