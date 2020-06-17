package com.consultorio.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.consultorio.application.Util;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Medico;
import com.consultorio.model.Pessoa;

@Named
@RequestScoped
public class LoginController extends Controller<Pessoa> {

	private static final long serialVersionUID = -7481072779220340737L;

	private Pessoa pessoa;
	UsuarioController usCon = new UsuarioController();

	public String logar() {
		FacesContext context = FacesContext.getCurrentInstance();
		String senhacrip = Util.hashSHA256(getEntity().getSenha());
		System.out.println("Email: " + entity.getEmail() + " senha: " + senhacrip);
		if (usCon.logar(entity.getEmail(), senhacrip) == true) {
			Util.addMessageError("Login Efetuado com sucesso");
			return "paciente.xhtml?faces-redirect=true";
		}
		Util.addMessageError("Email ou senha incorretos");
		return " . ";
	}

	public Pessoa getPessoa(String email, String senha) {
		try {
			EntityManager em = JPAFactory.getEntityManager();
			Query query = em
					.createQuery("SELECT p FROM Pessoa p where p.email = :email and p.senha = :senha");
			query.setParameter("email", "email");
			query.setParameter("senha", "senha");
			return entity;
		} catch (NoResultException e) {
			return null;
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public Pessoa getEntity() {
		if (entity == null)
			entity = new Pessoa();
		return entity;
	}

}
