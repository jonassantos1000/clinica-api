package com.app.med.project.api.domains.paciente;

import com.app.med.project.api.domains.endereco.Endereco;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@Embedded
	private Endereco endereco;
	private String email;
	private String telefone;

	public Paciente(DadosCadastroPaciente pacienteDTO) {
		this.email = pacienteDTO.email();
		this.endereco = new Endereco(pacienteDTO.endereco());
		this.nome = pacienteDTO.nome();
		this.telefone = pacienteDTO.telefone();
	}

}
