package com.consultorio.model;

import javax.persistence.Entity;

@Entity
public class Medicamento extends DefaultEntity<Medicamento> {

	private static final long serialVersionUID = -4432412034960896245L;
	
	String nome;
	
	

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}
}
