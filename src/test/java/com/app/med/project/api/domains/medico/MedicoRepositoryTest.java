package com.app.med.project.api.domains.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.app.med.project.api.domains.consulta.Consulta;
import com.app.med.project.api.domains.endereco.Endereco;
import com.app.med.project.api.domains.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest{
	
	@Autowired
	protected TestEntityManager em;
	
	@Autowired
	protected MedicoRepository medicoRepository;
	
	@Test
	@DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
	void consultarMedicoAleatorioPorEspecialidadeCenario1() {
		var proximaQuintaFeiraAs12 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))
				.atTime(10, 0);
		
		var medico = cadastrarMedico("medico", "joao@gmail.com", "123456", Especialidade.DERMATOLOGIA);
		var paciente = cadastrarPaciente("paciente", "paciente@terra.com", "123456789");
		cadastrarConsulta(medico, paciente, proximaQuintaFeiraAs12);
		
		var medicoLivre = medicoRepository.consultarMedicoAleatorioPorEspecialidade(Especialidade.CARDIOLOGIA, proximaQuintaFeiraAs12);
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("Deveria devolver medico disponivel para consulta")
	void consultarMedicoAleatorioPorEspecialidadeCenario2() {
		var proximaQuintaFeiraAs12 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))
				.atTime(10, 0);
		
		var medico = cadastrarMedico("medico", "joao@gmail.com", "123456", Especialidade.CARDIOLOGIA);
	
		var medicoLivre = medicoRepository.consultarMedicoAleatorioPorEspecialidade(Especialidade.CARDIOLOGIA, proximaQuintaFeiraAs12);
		
		assertThat(medicoLivre).isEqualTo(medico);
	}
	
	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
		em.persist(new Consulta(null, medico, paciente, data, null));
	}
	
	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
		var medico = new Medico(null, nome, email, crm, "123456789", true, especialidade, dadosEndereco());
		em.persist(medico);
		return medico; 
	}
	
	private Endereco dadosEndereco() {
		var endereco = new Endereco();
		endereco.setBairro("teste");
		endereco.setCep("12354844");
		endereco.setCidade("SP");
		endereco.setComplemento("teste");
		endereco.setLogradouro("teste");
		endereco.setNumero("1");
		endereco.setUf("sp");
		return endereco;
	}

	private Paciente cadastrarPaciente(String nome, String email, String telefone) {
		var paciente = new Paciente(null, "paciente", dadosEndereco(), "paciente@terra.com", "123456789");
		em.persist(paciente);
		return paciente;
	}
}
