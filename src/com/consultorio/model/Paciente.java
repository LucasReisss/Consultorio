package com.consultorio.model;

import javax.persistence.Entity;

import com.consultorio.model.validation.PacienteValidation;
import com.consultorio.model.validation.Validation;

@Entity
public class Paciente extends Pessoa {
	
	private static final long serialVersionUID = -8722060693269364691L;
	
	private Exame exame;
	
	private Convenio convenio;
	
	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	@Override
	public Validation<Paciente> getValidation() {
		return new PacienteValidation();
	}

	
	
	

}
