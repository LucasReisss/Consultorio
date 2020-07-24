package com.consultorio.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.consultorio.validation.Validation;

@Entity
public class Paciente extends DefaultEntity<Paciente> {
	
	private static final long serialVersionUID = -8722060693269364691L;
	
	private Exame exame;
	
	@OneToOne(cascade = CascadeType.ALL)
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
		// TODO Auto-generated method stub
		return null;
	}
}
