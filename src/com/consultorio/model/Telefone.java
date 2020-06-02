package com.consultorio.model;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Telefone extends DefaultEntity<Telefone> {
	private static final long serialVersionUID = 4741181020229599799L;
	String ddd;
	String numero;
	@Override
	public Validation<Telefone> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}
}
