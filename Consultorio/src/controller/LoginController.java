package controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import model.Paciente;
import model.Usuario;

@Named
@ViewScoped
public class LoginController extends Controller<Usuario> {
	
	private static final long serialVersionUID = -2882650718218148954L;
	

	@Override
	public Usuario getEntity() {
		if (entity == null)
			entity = new Usuario();
		return entity;
	}
	
	public void compararDados(Usuario usuario) {
		
		
	}
	
	
	public void realizarCadastro() {
		
	}
	
	public String criptografarSenha(Usuario usuario) throws Exception {
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(usuario.getSenha().getBytes("UTF-8"));
		 
		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
		  hexString.append(String.format("%02X", 0xFF & b));
		}
		String senha = hexString.toString();
		return senha;
	}
	 
	

}
