package com.consultorio.model;

import java.util.Date;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Agenda extends DefaultEntity<Agenda>{

	private static final long serialVersionUID = 3630079033623890740L;
	
	String nome;
	Date atendimento;
	Date horario;
	Paciente paciente;
	Telefone telefone;
	Convenio convenio;
	String observacao;
	Boolean retorno;
	Medico medico;
	
	@Override
	public Validation<Agenda> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
