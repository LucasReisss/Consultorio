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
import com.consultorio.model.Paciente;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.MedicoRepository;

@Named
@ViewScoped
public class MedicoController extends Controller<Pessoa> {

	private static final long serialVersionUID = -3862624597114610891L;
	
	private String filtro;
	private List<Paciente> listaMedico;
	
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
			if (getEntity().getMedico().getEspecialidade() != null 
					&& getEntity().getMedico().getEspecialidade().getId() == null) {
				getEntity().getMedico().setEspecialidade(null);
			}
			getEntity().getMedico().setEspecialidade(getEntity().getMedico().getEspecialidade());
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
		if (getEntity().getMedico().getEspecialidade() == null)
			getEntity().getMedico().setEspecialidade(new EspecialidadeMedica());
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
		getEntity().getMedico().setEspecialidade(entity);
	}

	@Override
	public Pessoa getEntity() {
		if(entity == null) {
			entity = new Pessoa();
			if(entity.getMedico() == null) {
				entity.setMedico(new Medico());
				if (entity.getMedico().getEspecialidade() == null) {
					entity.getMedico().setEspecialidade(new EspecialidadeMedica());					
				}
			}
		}
	
		return entity;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Paciente> getListaMedico() {
		if(listaMedico == null)
			listaMedico = new ArrayList<Paciente>();
		return listaMedico;
	}

	
	
}
