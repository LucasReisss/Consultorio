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
			throw new ValidationException("CPF Inválido. Este CPF já está sendo utilizado.");
		}
	}

	private void validaEmail(Pessoa entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsEmail(entity.getEmail(), entity.getId())) {
			throw new ValidationException("Email Inválido. Este e-mail já está sendo utilizado.");
		}
	}

	private void validaRg(Pessoa entity) throws ValidationException {
		PacienteRepository repo = new PacienteRepository();
		if (repo.containsRg(entity.getRg(), entity.getId())) {
			throw new ValidationException("Rg Inválido. Este rg já está sendo utilizado.");
		}
	}

}
