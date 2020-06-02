package com.consultorio.model;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Endereco extends DefaultEntity<Endereco> {

	static final long serialVersionUID = -4473430838805813795L;
	
	String cep;
	String lagradouro;
	String numero;
	String bairro;
	String cidade;
	UF uf;
	
	
	
	public String getCep() {
		return cep;
	}



	public void setCep(String cep) {
		this.cep = cep;
	}



	public String getLagradouro() {
		return lagradouro;
	}



	public void setLagradouro(String lagradouro) {
		this.lagradouro = lagradouro;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getBairro() {
		return bairro;
	}



	public void setBairro(String bairro) {
		this.bairro = bairro;
	}



	public String getCidade() {
		return cidade;
	}



	public void setCidade(String cidade) {
		this.cidade = cidade;
	}



	public UF getUf() {
		return uf;
	}



	public void setUf(UF uf) {
		this.uf = uf;
	}



	@Override
	public Validation<Endereco> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
