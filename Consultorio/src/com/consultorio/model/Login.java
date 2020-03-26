package com.consultorio.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Login extends DefaultEntity<Login> {
	
	private static final long serialVersionUID = -422953916537710258L;
	
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(length = 50, nullable = false)
	private String email;
	@Column(length = 20, nullable = false)
	private String senha;
	
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
	
}
