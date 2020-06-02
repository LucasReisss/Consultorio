package com.consultorio.model;

import java.util.Date;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Pessoa extends DefaultEntity<Pessoa> {

	private static final long serialVersionUID = -298974611494912586L;
	
	String nome;
	Date dataNascimento;
	String rg;
	String cpf;
	String naturalidade;
	String emissor;
	Endereco endereco;
	Telefone telefone;
	
	
	

	@Override
	public Validation<Pessoa> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
