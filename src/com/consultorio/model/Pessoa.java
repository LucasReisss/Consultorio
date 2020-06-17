package com.consultorio.model;

import java.util.Date;

import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
public class Pessoa extends DefaultEntity<Pessoa> {

	private static final long serialVersionUID = -298974611494912586L;
	
	private String nome;
	private Date dataNascimento;
	private String rg;
	private String cpf;
	private String naturalidade;
	private String emissor;
	private Endereco endereco;
	private Telefone telefone;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	@Override
	public Validation<Pessoa> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
