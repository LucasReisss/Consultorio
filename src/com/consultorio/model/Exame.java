package com.consultorio.model;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Exame extends DefaultEntity<Exame> {

	private static final long serialVersionUID = 5667175255157124925L;
	
	Tipo tipo;
	Medicamento medicamento;
	String descricao;
	String posologia;

	@Override
	public Validation<Exame> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
