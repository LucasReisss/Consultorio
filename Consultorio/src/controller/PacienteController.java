package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import factory.JPAFactory;
import model.Paciente;

@Named
@ViewScoped
public class PacienteController extends Controller<Paciente> {

	private static final long serialVersionUID = 5133323952101528105L;
	
	private String filtro;
	private List<Paciente> listaPaciente;
	
	public void pesquisar() {
		EntityManager em = JPAFactory.getEntityManager();
		Query query = em.createQuery(
				"Select a "
			  + "From Paciente a "
			  + "Where upper(a.nome) like upper(:filtro)");
		query.setParameter("filtro", "%"+ getFiltro() + "%");
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

	public List<Paciente> getListaAluno() {
		if (listaPaciente == null)
			listaPaciente = new ArrayList<Paciente>();
		return listaPaciente;
	}
}
