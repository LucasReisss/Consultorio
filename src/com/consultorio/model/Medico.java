package com.consultorio.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.consultorio.model.validation.MedicoValidation;
import com.consultorio.model.validation.Validation;

@Entity
public class Medico extends Pessoa {

	private static final long serialVersionUID = -755582211013832728L;
	
	@ManyToOne()
	@JoinColumn(name = "idespecialidade", nullable = true)
	private EspecialidadeMedica especialidade;

	public EspecialidadeMedica getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadeMedica especialidade) {
		this.especialidade = especialidade;
	}
	
	@Override
	public Validation<Medico> getValidation() {
		return new MedicoValidation();
	}
	
	

}
