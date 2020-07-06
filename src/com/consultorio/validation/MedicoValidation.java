package com.consultorio.validation;

import java.time.LocalDate;

import com.consultorio.application.ValidationException;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.MedicoRepository;

public class MedicoValidation {

	public void validate(Pessoa entity) throws ValidationException {
		validaDataAniversario(entity);
		
		validaEmail(entity);
		
		validaCpf(entity);
	}
	
	private void validaCpf(Pessoa entity) throws ValidationException {
		MedicoRepository repo = new MedicoRepository();
		if(repo.containsCpf(entity.getCpf(), entity.getId())) {
			throw new ValidationException("CPF Inválido. Este CPF já está sendo utilizado.");
		}
	}

	private void validaEmail(Pessoa entity) throws ValidationException {
		MedicoRepository repo = new MedicoRepository();
		if (repo.containsEmail(entity.getId(), entity.getEmail())) {
			throw new ValidationException("Email Inválido. Este e-mail já está sendo utilizado.");
		}
	}
	
	private void validaDataAniversario(Pessoa entity) throws ValidationException {
		LocalDate data = new java.sql.Date((entity).getDataNascimento().getTime()).toLocalDate();
		LocalDate dataLimite = LocalDate.now().minusYears(18);
		
		if (data.isAfter(dataLimite)) {
			throw new ValidationException("Data Inválida. O médico não pode ser menor de idade.");
		}
	}

}
