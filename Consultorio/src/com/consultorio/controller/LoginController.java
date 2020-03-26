package com.consultorio.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultorio.application.Util;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Login;

@Named
@ViewScoped
public class LoginController extends Controller<Login> {

	private static final long serialVersionUID = 5361424037386272291L;
	private String filtro;
	private List<Login> listaLogin;

	public void pesquisar() {
		EntityManager em = JPAFactory.getEntityManager();
		Query query = em.createQuery("Select p " + "From Paciente p " + "Where upper(p.nome) like upper(:filtro)");
		query.setParameter("filtro", "%" + getFiltro() + "%");
		listaLogin = query.getResultList();
	}

	public void logar() {
		
	}
	
	@Override
	public Login getEntity() {
		if (entity == null) {
			entity = new Login();
		}
		return entity;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Login> getListaLogin() {
		if (listaLogin == null) {
			listaLogin = new ArrayList<Login>();
		}
		return listaLogin;
	}

}
