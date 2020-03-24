package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Paciente;

@Named
@ViewScoped
public class PacienteController extends Controller<Paciente> { 

	private static final long serialVersionUID = -7996231487557010298L;
	private String filtro;
	private List<Paciente> listaPaciente;

	public void pesquisar() {
		EntityManager em = JPAFactory.getEntityManager();
		Query query = em.createQuery("Select p " + "From Paciente p " + "Where upper(p.nome) like upper(:filtro)");
		query.setParameter("filtro", "%" + getFiltro() + "%");
		listaPaciente = query.getResultList();
	}
	
	@Override
	public Paciente getEntity() {
		if (entity == null)
			entity = new Paciente();
		return entity;
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

}
