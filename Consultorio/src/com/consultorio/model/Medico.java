package com.consultorio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Medico extends DefaultEntity<Medico> {

	private static final long serialVersionUID = -755582211013832728L;
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(length = 50)
	private String email;
	@Column(length = 100, nullable = false)
	private String senha;
	@Column(length = 20)
	private String cpf;
	@Column(length = 20)
	private String rg;
	@Column(length = 20)
	private String naturalidade;
	@Column(length = 20)
	private String emissor;
	
	@ManyToOne
	@JoinColumn(name = "idespecialidade")
	private EspecialidadeMedica especialidade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public EspecialidadeMedica getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadeMedica especialidade) {
		this.especialidade = especialidade;
	}
	
	

}
