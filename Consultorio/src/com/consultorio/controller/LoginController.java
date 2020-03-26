package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import com.consultorio.application.Util;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Usuario;

@Named
@RequestScoped
public class LoginController extends Controller<Usuario> {

	private static final long serialVersionUID = -7481072779220340737L;

	private Usuario usuario;
	UsuarioController usCon = new UsuarioController();
	
	
	public String login() {
		
		
		
//		List<Usuario> usuarios = new ArrayList<Usuario>();
//		usuarios = (List<Usuario>) getUsuario(getEntity().getEmail(), getEntity().getSenha());
//
//		for (Usuario usuarioDaLista : usuarios) {
//			if (getUsuario().getEmail().equals(usuarioDaLista.getEmail())) {
//				getUsuario().setSenha(Util.hashSHA256(getUsuario().getSenha()));
//				if (getUsuario().getSenha().equals(usuarioDaLista.getSenha())) {
//					System.out.println("igual");
//
//					if (usuario != null) {
//						// armazenando um usuario na sessao
//						return "paciente.xhtml?faces-redirect=true";
//
//					}
//
//				}
//			}
//			
//	}
//		Util.addMessageError("Usuário ou senha Inválido.");
//		return null;
	

		String senhacrip = Util.hashSHA256(getEntity().getSenha());
		usuario = getUsuario(getEntity().getEmail(), senhacrip);
		
		
	if(usuario != null) {
		Util.addMessageInfo("Login realizado com Sucesso");
			return "paciente.xhtml?faces-redirect=true";
		}
			Util.addMessageError("Usuário ou senha Inválido");
			return null;
	}


	public Usuario getUsuario(String email, String senha) {
		try {
			EntityManager em = JPAFactory.getEntityManager();
			Query query = em.createQuery("SELECT m " + "FROM Usuario m " + "where m.email = :email and m.senha = :senha");
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
	
	
	public String logar() {
		FacesContext context = FacesContext.getCurrentInstance();
		String senhacrip = Util.hashSHA256(getEntity().getSenha());
		System.out.println("Email: " + entity.getEmail() + " senha: " + senhacrip);
		if(usCon.logar(entity.getEmail(), senhacrip) == true) {
			return "paciente.xhtml?faces-redirect=true";
		}
		context.addMessage(null, new FacesMessage("Email ou senha incorretos"));
		return " . ";
	}

}
