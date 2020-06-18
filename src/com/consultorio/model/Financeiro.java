package com.consultorio.model;

import java.util.Date;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Financeiro extends DefaultEntity<Financeiro> {

	private static final long serialVersionUID = -2424463964056181252L;
	
	Paciente paciente;
	String cpf;
	String descricao;
	Date data;
	Double valor;
	Medico medico;
	
	@Override
	public Validation<Financeiro> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
