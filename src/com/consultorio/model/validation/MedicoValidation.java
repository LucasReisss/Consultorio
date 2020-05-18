package com.consultorio.model.validation;

import java.time.LocalDate;

import com.consultorio.application.ValidationException;
import com.consultorio.model.Medico;
import com.consultorio.repository.MedicoRepository;

public class MedicoValidation implements Validation<Medico> {

	@Override
	public void validate(Medico entity) throws ValidationException {
		validaDataAniversario(entity);
		
		validaEmail(entity);
		
		validaCpf(entity);
	}
	
	private void validaCpf(Medico entity) throws ValidationException {
		MedicoRepository repo = new MedicoRepository();
		if(repo.contains(entity.getCpf(), entity.getId())) {
			throw new ValidationException("CPF Inválido. Este CPF já está sendo utilizado.");
		}
	}

	private void validaEmail(Medico entity) throws ValidationException {
		MedicoRepository repo = new MedicoRepository();
		if (repo.contains(entity.getId(), entity.getEmail())) {
			throw new ValidationException("Email Inválido. Este e-mail já está sendo utilizado.");
		}
	}
	
	private void validaDataAniversario(Medico entity) throws ValidationException {
		LocalDate data = new java.sql.Date(entity.getDataAniversario().getTime()).toLocalDate();
		LocalDate dataLimite = LocalDate.now().minusYears(18);
		
		if (data.isAfter(dataLimite)) {
			throw new ValidationException("Data Inválida. O médico não pode ser menor de idade.");
		}
	}

}
