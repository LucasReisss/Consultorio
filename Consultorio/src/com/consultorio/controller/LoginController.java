package com.consultorio.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.consultorio.application.Util;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Usuario;

@Named
@RequestScoped
public class LoginController extends Controller<Usuario> {

	private static final long serialVersionUID = -7481072779220340737L;

	private Usuario usuario;
	UsuarioController usCon = new UsuarioController();

	public String logar() {
		FacesContext context = FacesContext.getCurrentInstance();
		String senhacrip = Util.hashSHA256(getEntity().getSenha());
		System.out.println("Email: " + entity.getEmail() + " senha: " + senhacrip);
		if (usCon.logar(entity.getEmail(), senhacrip) == true) {
			return "paciente.xhtml?faces-redirect=true";
		}
		Util.addMessageError("Email ou senha incorretos");
		return " . ";
	}

	public Usuario getUsuario(String email, String senha) {
		try {
			EntityManager em = JPAFactory.getEntityManager();
			Query query = em
					.createQuery("SELECT m " + "FROM Usuario m " + "where m.email = :email and m.senha = :senha");
			query.setParameter("email", "email");
			query.setParameter("senha", "senha");
			return entity;
		} catch (NoResultException e) {
			return null;
		}
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public Usuario getEntity() {
		if (entity == null)
			entity = new Usuario();
		return entity;
	}

}
