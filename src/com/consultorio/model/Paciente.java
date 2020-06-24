package com.consultorio.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.consultorio.validation.Validation;

@Entity
@DiscriminatorValue("Paciente")
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
	public Validation<Pessoa> getValidation() {
		return super.getValidation();
	}
}
