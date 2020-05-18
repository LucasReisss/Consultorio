package com.consultorio.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Paciente extends DefaultEntity<Paciente>{

	private static final long serialVersionUID = 5683363782986414566L;
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(length = 20)
	private String cpf;
	@Column(length = 20)
	private String rg;
	@Column(length = 20)
	private String naturalidade;
	@Column(length = 20)
	private String emissor;
	@Column(length = 50)
	private String email;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	public String getEmissor() {
		return emissor;
	}
	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public Validation<Paciente> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}	

}
