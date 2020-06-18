package com.consultorio.model;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class ConvenioF extends DefaultEntity<ConvenioF> {

	private static final long serialVersionUID = -8006415021250494218L;
	
	String nome;
	String registroAns;
	
	

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getRegistroAns() {
		return registroAns;
	}



	public void setRegistroAns(String registroAns) {
		this.registroAns = registroAns;
	}

	@Override
	public Validation<ConvenioF> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
