package com.consultorio.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.consultorio.validation.Validation;


@Entity
public class Medico extends DefaultEntity<Medico> {
	
	private static final long serialVersionUID = -3807693099398317608L;
	
	@ManyToOne
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
		// TODO Auto-generated method stub
		return null;
	}
	
}
