package com.consultorio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import com.consultorio.model.validation.Validation;

@Entity
@NamedQuery(name = "logar", query = "SELECT m FROM Usuario m WHERE m.email = :email and m.senha = :senha")
public class Usuario extends DefaultEntity<Usuario> {
	
	private static final long serialVersionUID = -422953916537710258L;
	
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(length = 50, nullable = false)
	private String email;
	@Column(length = 100, nullable = false)
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
	@Override
	public Validation<Usuario> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
