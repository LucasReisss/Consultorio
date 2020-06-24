package com.consultorio.model;

import javax.persistence.Entity;

import com.consultorio.validation.Validation;

@Entity
public class Exame extends DefaultEntity<Exame> {

	private static final long serialVersionUID = 5216381410759993045L;

	private String anamnese;
	
	private String evolucao;
	
	private String diagnostico;
	
	private Prescricao prescricao;
	
	private String atestado;
	
	private String historico;
	
	public String getAnamnese() {
		return anamnese;
	}

	public void setAnamnese(String anamnese) {
		this.anamnese = anamnese;
	}

	public String getEvolucao() {
		return evolucao;
	}

	public void setEvolucao(String evolucao) {
		this.evolucao = evolucao;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}

	public String getAtestado() {
		return atestado;
	}

	public void setAtestado(String atestado) {
		this.atestado = atestado;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	@Override
	public Validation<Exame> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
