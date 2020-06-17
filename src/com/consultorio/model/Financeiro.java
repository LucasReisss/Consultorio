package com.consultorio.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Financeiro extends DefaultEntity<Financeiro> {

	private static final long serialVersionUID = -2424463964056181252L;
	
	Paciente paciente;
	String cpf;
	String descricao;
	Date data;
	Double valor;
	Medico medico;



}
