package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.Util;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Usuario;
import com.consultorio.repository.Repository;

@Named
@ViewScoped
public class UsuarioController extends Controller<Usuario> {

	private static final long serialVersionUID = -3305082947567412162L;
	private String filtro;
	private List<Usuario> listaUsuario;

	public void pesquisar() {
		EntityManager em = JPAFactory.getEntityManager();
		Query query = em.createQuery("Select m " + "From Usuario m " + "Where upper(m.nome) like upper(:filtro)");
		query.setParameter("filtro", "%" + getFiltro() + "%");
		listaUsuario = query.getResultList();
	}

	@Override
	public void salvar() {
		Repository<Usuario> r = new Repository<Usuario>();
		try {
			r.beginTransaction();
			getEntity().setSenha(Util.hashSHA256(getEntity().getSenha()));
			r.salvar(getEntity());
			r.commitTransaction();
		} catch (RepositoryException e) {
			e.printStackTrace();
			r.rollbackTransaction();
			Util.addMessageError("Problema ao salvar.");
			return;
		}
		limpar();
		Util.addMessageInfo("Cadastro realizado com sucesso.");
	}

	@Override
	public Usuario getEntity() {
		if (entity == null)
			entity = new Usuario();
		return entity;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null)
			listaUsuario = new ArrayList<Usuario>();
		return listaUsuario;
	}

	public boolean logar(String email, String senha) {
		EntityManager em = JPAFactory.getEntityManager();
		System.out.println("entrou aqui");
		try {
			Usuario user = (Usuario) em.createNamedQuery("logar", Usuario.class).setParameter("email", email)
					.setParameter("senha", senha).getSingleResult();
			if (user != null) {
				return true;
			}
			return false;
		} catch (NoResultException e) {
			return false;
		}

	}

}
