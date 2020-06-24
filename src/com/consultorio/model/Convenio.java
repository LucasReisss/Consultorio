package com.consultorio.model;

import java.util.Date;

import javax.persistence.Entity;

import com.consultorio.validation.Validation;

@Entity
public class Convenio extends DefaultEntity<Convenio> {

	private static final long serialVersionUID = 3189926229081725200L;
	
	private ConvenioF convenio;
	private String carteirinha;
	private String plano;
	private Date validade;

	public ConvenioF getConvenio() {
		return convenio;
	}
	
	public void setConvenio(ConvenioF convenio) {
		this.convenio = convenio;
	}

	public String getCarteirinha() {
		return carteirinha;
	}

	public void setCarteirinha(String carteirinha) {
		this.carteirinha = carteirinha;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	@Override
	public Validation<Convenio> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}
}
