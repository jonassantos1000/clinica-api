package com.app.med.project.api.domains;

import com.app.med.project.api.domains.dto.DadosCadastroMedico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String crm;
	private String telefone;
	
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	@Embedded
	private Endereco endereco;
	
	public Medico(DadosCadastroMedico medicoDTO) {
		this.nome = medicoDTO.nome();
		this.crm = medicoDTO.crm();
		this.email = medicoDTO.email();
		this.telefone = medicoDTO.telefone();
		this.especialidade = medicoDTO.especialidade();
		this.endereco = new Endereco (medicoDTO.endereco());
	}
}
