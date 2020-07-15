package com.consultorio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import com.consultorio.validation.Validation;

@Entity
public class Medico extends DefaultEntity<Medico> {

	private static final long serialVersionUID = -3807693099398317608L;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "medico_esp", joinColumns = { @JoinColumn(name = "idmedico") }, inverseJoinColumns = {
			@JoinColumn(name = "idespecialidade") })
	private List<EspecialidadeMedica> listaEspecialidade;

	public List<EspecialidadeMedica> getListaEspecialidade() {
		return listaEspecialidade;
	}

	public void setListaEspecialidade(List<EspecialidadeMedica> listaEspecialidade) {
		this.listaEspecialidade = listaEspecialidade;
	}

	@Override
	public Validation<Medico> getValidation() {
		// TODO Auto-generated method stub
		return null;
	}

}
