package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.consultorio.model.Administrador;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.AdministradorRepository;

@Named
@ViewScoped
public class AdministradorController extends Controller<Pessoa> {

	private static final long serialVersionUID = -3862624597114610891L;
	
	private String filtro;
	private List<Administrador> listaAdministrador;
	
	public void pesquisar() {
		AdministradorRepository repo = new AdministradorRepository();
		listaAdministrador = repo.findByNome(getFiltro());
	}
	
	@Override
	public void editar(int id) {
		super.editar(id);
	}
	
//	public void abrirAdministradorListing() {
//		AdministradorListing listing = new AdministradorListing();
//		listing.open();
//	}
//	
//	public void obterAdministradorListing(SelectEvent event) {
//		Administrador entity = (Administrador) event.getObject();
//		getEntity().setAdministrador(entity);
//	}

	@Override
	public Administrador getEntity() {
		if(entity == null) {
			entity = new Administrador();
		}
		
		return (Administrador) entity;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Administrador> getListaAdministrador() {
		if(listaAdministrador == null)
			listaAdministrador = new ArrayList<Administrador>();
		return listaAdministrador;
	}

	
	
}
