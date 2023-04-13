package com.app.med.project.api.domains;

import com.app.med.project.api.domains.dto.DadosEndereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
public class Endereco {
	
	private String logradouro;
	private String bairro;
	private String cep;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;
	
	
	
	public Endereco() {
	}



	public Endereco(DadosEndereco enderecoDTO) {
		this.bairro = enderecoDTO.bairro();
		this.logradouro = enderecoDTO.logradouro();
		this.numero = enderecoDTO.numero();
		this.cep = enderecoDTO.cep();
		this.cidade = enderecoDTO.cidade();
		this.complemento = enderecoDTO.complemento();
		this.uf = enderecoDTO.uf();
	}
}
