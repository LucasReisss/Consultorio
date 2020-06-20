package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.Util;
import com.consultorio.application.ValidationException;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.Repository;

@Named
@ViewScoped
public class UsuarioController extends Controller<Pessoa> {

	private static final long serialVersionUID = -3305082947567412162L;
	private String filtro;
	private List<Pessoa> listaPessoa;

	public void pesquisar() {
		EntityManager em = JPAFactory.getEntityManager();
		Query query = em.createQuery("Select p " + "From Pessoa p " + "Where upper(p.nome) like upper(:filtro)");
		query.setParameter("filtro", "%" + getFiltro() + "%");
		listaPessoa = query.getResultList();
	}

	@Override
	public void salvar() {
		Repository<Pessoa> r = new Repository<Pessoa>();
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
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		limpar();
		Util.addMessageInfo("Cadastro realizado com sucesso.");
	}

	@Override
	public Pessoa getEntity() {
		if (entity == null)
			entity = new Pessoa();
		return entity;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Pessoa> getListaPessoa() {
		if (listaPessoa == null)
			listaPessoa = new ArrayList<Pessoa>();
		return listaPessoa;
	}

	public boolean logar(String email, String senha) {
		EntityManager em = JPAFactory.getEntityManager();
		System.out.println("entrou aqui");
		try {
			
			Pessoa user =  (Pessoa) em.createNamedQuery("logar", Pessoa.class).setParameter("email", email)
					.setParameter("senha", senha).getSingleResult();
			
			if (user != null) {
				return true;
			}
			return false;
		} catch (NoResultException e) {
			return false;
		}

	}
	
	public Pessoa logaR(String email, String senha) {
		EntityManager em = JPAFactory.getEntityManager();
		System.out.println("entrou aqui");
		try {
			
			Pessoa user =  (Pessoa) em.createNamedQuery("logar", Pessoa.class).setParameter("email", email)
					.setParameter("senha", senha).getSingleResult();
			
			if (user != null) {
				return user;
			}
			return user;
		} catch (NoResultException e) {
			Pessoa user = null;
			return user;
		}

	}

}
