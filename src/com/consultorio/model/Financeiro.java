package com.consultorio.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.consultorio.validation.Validation;

@Entity
public class Financeiro extends DefaultEntity<Financeiro> {

	private static final long serialVersionUID = -2424463964056181252L;
	
	private String paciente;
	private String cpf;
	private String descricao;
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data;
	private Double valor;
	private String medico;
	


	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}


	@Override
	public Validation<Financeiro> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
