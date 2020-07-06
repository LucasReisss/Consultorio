package com.consultorio.validation;

import com.consultorio.application.ValidationException;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.PacienteRepository;

public class PacienteValidation {

	public void validate(Pessoa entity) throws ValidationException {
		validaEmail(entity);

		validaCpf(entity);

		validaRg(entity);
	}

	private void validaCpf(Pessoa entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsCpf(entity.getCpf(), entity.getId())) {
			throw new ValidationException("CPF Inv�lido. Este CPF j� est� sendo utilizado.");
		}
	}

	private void validaEmail(Pessoa entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsEmail(entity.getEmail(), entity.getId())) {
			throw new ValidationException("Email Inv�lido. Este e-mail j� est� sendo utilizado.");
		}
	}

	private void validaRg(Pessoa entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsRg(entity.getRg(), entity.getId())) {
			throw new ValidationException("Rg Inv�lido. Este rg j� est� sendo utilizado.");
		}
	}

}
