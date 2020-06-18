package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.Util;
import com.consultorio.application.ValidationException;
import com.consultorio.listing.EspecialidadeMedicaListing;
import com.consultorio.model.EspecialidadeMedica;
import com.consultorio.model.Medico;
import com.consultorio.model.Pessoa;
import com.consultorio.model.validation.MedicoValidation;
import com.consultorio.repository.MedicoRepository;

@Named
@ViewScoped
public class MedicoController extends Controller<Pessoa> {

	private static final long serialVersionUID = -3862624597114610891L;
	
	private String filtro;
	private List<Medico> listaMedico;
	
	public void pesquisar() {
		MedicoRepository repo = new MedicoRepository();
		listaMedico = repo.findByNome(getFiltro());
	}
	
	@Override
	public void salvar() {
		MedicoRepository r = new MedicoRepository();
		try {
			r.beginTransaction();
			getEntity().setSenha(Util.hashSHA256(getEntity().getSenha()));
			if (getEntity().getEspecialidade() != null && getEntity().getEspecialidade().getId() == null) {
				getEntity().setEspecialidade(null);
			}
			r.salvar(getEntity());
			r.commitTransaction();
		} catch (RepositoryException e) {
			e.printStackTrace();
			r.rollbackTransaction();
			Util.addMessageError("Problema ao salvar.");
			return;
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
			r.rollbackTransaction();
			Util.addMessageError(e.getMessage());
			return;
		}
		limpar();
		Util.addMessageInfo("Cadastro realizado com sucesso.");
	}
	
	@Override
	public void editar(int id) {
		super.editar(id);
		if (getEntity().getEspecialidade() == null)
			getEntity().setEspecialidade(new EspecialidadeMedica());
	}
	
//	public void abrirMedicoListing() {
//		MedicoListing listing = new MedicoListing();
//		listing.open();
//	}
//	
//	public void obterMedicoListing(SelectEvent event) {
//		Medico entity = (Medico) event.getObject();
//		getEntity().setMedico(entity);
//	}
	
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
			((Medico) entity).setEspecialidade(new EspecialidadeMedica());
		}
		
		return (Medico) entity;
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
