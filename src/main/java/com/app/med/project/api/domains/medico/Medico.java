package com.app.med.project.api.domains.medico;

import com.app.med.project.api.domains.endereco.Endereco;

import jakarta.persistence.Column;
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
import lombok.Setter;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	private String nome;
	private String email;
	@Column(unique = true)
	private String crm;
	private String telefone;
	private Boolean ativo;

	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;

	@Embedded
	private Endereco endereco;

	public Medico(DadosCadastroMedico medicoDTO) {
		this.nome = medicoDTO.nome();
		this.crm = medicoDTO.crm();
		this.email = medicoDTO.email();
		this.telefone = medicoDTO.telefone();
		this.ativo = true;
		this.especialidade = medicoDTO.especialidade();
		this.endereco = new Endereco(medicoDTO.endereco());
	}
	
	public void excluir(boolean status) {
		this.ativo = status;
	}
}
