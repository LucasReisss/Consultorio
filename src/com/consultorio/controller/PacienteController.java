package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.consultorio.listing.PacienteListing;
import com.consultorio.model.Paciente;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.PacienteRepository;

@Named
@ViewScoped
public class PacienteController extends Controller<Pessoa> { 

	private static final long serialVersionUID = -7996231487557010298L;
	private String filtro;
	private List<Paciente> listaPaciente;

	public void pesquisar() {
		PacienteRepository repo = new PacienteRepository();
		listaPaciente = repo.findByNome(getFiltro());
	}
	
	@Override
	public Paciente getEntity() {
		if (entity == null)
			entity = new Paciente();
		return (Paciente) entity;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Paciente> getListaPaciente() {
		if (listaPaciente == null)
			listaPaciente = new ArrayList<Paciente>();
		return listaPaciente;
	}

	public void abrirPacienteListing() {
		PacienteListing listing = new PacienteListing();
		listing.open();
	}
	
	public void obterPacienteListing(SelectEvent event) {
		Paciente entity = (Paciente) event.getObject();
		setEntity(entity);
	}
}
