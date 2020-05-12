package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.consultorio.listing.EspecialidadeMedicaListing;
import com.consultorio.model.EspecialidadeMedica;
import com.consultorio.model.Medico;
import com.consultorio.repository.MedicoRepository;

@Named
@ViewScoped
public class MedicoController extends Controller<Medico> {

	private static final long serialVersionUID = -3862624597114610891L;
	
	private String filtro;
	private List<Medico> listaMedico;
	
	public void pesquisar() {
		MedicoRepository repo = new MedicoRepository();
		listaMedico = repo.findByNome(getFiltro());
	}
	
	public void abrirEspecialidadeListing() {
		EspecialidadeMedicaListing listing = new EspecialidadeMedicaListing();
		listing.open();
	}
	
	public void obterEspecialidadeListing(SelectEvent event) {
		EspecialidadeMedica entity = (EspecialidadeMedica) event.getObject();
		getEntity().setEspecialidade(entity);
	}

	@Override
	public Medico getEntity() {
		if(entity == null) {
			entity = new Medico();
			entity.setEspecialidade(new EspecialidadeMedica());
		}
		return null;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Medico> getListaMedico() {
		if(listaMedico == null)
			listaMedico = new ArrayList<Medico>();
		return listaMedico;
	}

	
	
}