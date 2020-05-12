package com.consultorio.listing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.PrimeFaces;

import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Paciente;
import com.consultorio.repository.PacienteRepository;

@Named
@ViewScoped
public class PacienteListing extends Listing<Paciente> {

	private static final long serialVersionUID = -5274842308492045809L;
	private List<Paciente> list;
	private String filtro;
	
	@Override
	public Paciente getEntity() {
		if(entity == null) {
			entity = new Paciente();
		}
		return entity;
	}
	
	public void open() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", true);
		options.put("draggable", true);
		options.put("modal", true);
		options.put("width", 800);
		options.put("height", 500);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");

		PrimeFaces.current().dialog().openDynamic("pacientelisting", options, null);
	}

	public void pesquisar() {
		PacienteRepository repo = new PacienteRepository();
		list = repo.findByNome(filtro);
	}

	public void select(int id) {
		EntityManager em = JPAFactory.getEntityManager();
		setEntity((Paciente) em.find(Paciente.class, id));

		PrimeFaces.current().dialog().closeDynamic(getEntity());
	}

	public List<Paciente> getList() {
		if (list == null)
			list = new ArrayList<Paciente>();
		return list;
	}

	public void setList(List<Paciente> list) {
		this.list = list;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

}
