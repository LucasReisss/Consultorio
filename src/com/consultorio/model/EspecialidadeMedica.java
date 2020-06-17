package com.consultorio.model;

import javax.persistence.Entity;

@Entity
public class EspecialidadeMedica extends DefaultEntity<EspecialidadeMedica> {

	private static final long serialVersionUID = -6647418779683681549L;

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
}
