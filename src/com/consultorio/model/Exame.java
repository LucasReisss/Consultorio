package com.consultorio.model;

import javax.persistence.Entity;

@Entity
public class Exame extends DefaultEntity<Exame> {

	private static final long serialVersionUID = 5667175255157124925L;
	
	Tipo tipo;
	Medicamento medicamento;
	String descricao;
	String posologia;

	

}
