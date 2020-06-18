package com.consultorio.model.validation;

import java.time.LocalDate;

import com.consultorio.application.ValidationException;
import com.consultorio.model.Medico;
import com.consultorio.model.Paciente;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.MedicoRepository;
import com.consultorio.repository.PacienteRepository;

public class PessoaValidation implements Validation<Pessoa> {

	@Override
	public void validate(Pessoa entity) throws ValidationException {
		
		Paciente paciente = new Paciente();
		Medico medico = new Medico();
		
		if (entity.getClass().isInstance(paciente)) {
			
			validaCpfPaciente((Paciente) entity);

			validaEmailPaciente((Paciente) entity);
			
			validaRgPaciente((Paciente) entity);
		}
		
		if (entity.getClass().isInstance(medico)) {

			validaCpfMedico((Medico) entity);
			
			validaEmailMedico((Medico) entity);
			
			validaRgMedico((Medico) entity);
			
			validaDataAniversarioMedico((Medico) entity);
		}
		
	}
	
	private void validaCpfPaciente(Paciente entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsCpf(entity.getCpf(), entity.getId())) {
			throw new ValidationException("CPF Inválido. Este CPF já está sendo utilizado.");
		}
	}

	private void validaEmailPaciente(Paciente entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsEmail(entity.getEmail(), entity.getId())) {
			throw new ValidationException("Email Inválido. Este e-mail já está sendo utilizado.");
		}
	}

	private void validaRgPaciente(Paciente entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsRg(entity.getRg(), entity.getId())) {
			throw new ValidationException("Rg Inválido. Este rg já está sendo utilizado.");
		}
	}
	
	private void validaCpfMedico(Medico entity) throws ValidationException {
		MedicoRepository repo = new MedicoRepository();
		if(repo.containsCpf(entity.getCpf(), entity.getId())) {
			throw new ValidationException("CPF Inválido. Este CPF já está sendo utilizado.");
		}
	}

	private void validaEmailMedico(Medico entity) throws ValidationException {
		MedicoRepository repo = new MedicoRepository();
		if (repo.containsEmail(entity.getId(), entity.getEmail())) {
			throw new ValidationException("Email Inválido. Este e-mail já está sendo utilizado.");
		}
	}
	
	private void validaRgMedico(Medico entity) throws ValidationException {
		MedicoRepository repo = new MedicoRepository();
		if (repo.containsRg(entity.getRg(), entity.getId())) {
			throw new ValidationException("Rg Inválido. Este rg já está sendo utilizado.");
		}
	}
	
	private void validaDataAniversarioMedico(Medico entity) throws ValidationException {
		LocalDate data = new java.sql.Date(entity.getDataNascimento().getTime()).toLocalDate();
		LocalDate dataLimite = LocalDate.now().minusYears(18);
		
		if (data.isAfter(dataLimite)) {
			throw new ValidationException("Data Inválida. O médico não pode ser menor de idade.");
		}
	}

}
