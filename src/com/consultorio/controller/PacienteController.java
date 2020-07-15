package com.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.Util;
import com.consultorio.application.ValidationException;
import com.consultorio.listing.PacienteListing;
import com.consultorio.model.Paciente;
import com.consultorio.model.Pessoa;
import com.consultorio.model.Telefone;
import com.consultorio.repository.PacienteRepository;

@Named
@ViewScoped
public class PacienteController extends Controller<Pessoa> { 

	private static final long serialVersionUID = -7996231487557010298L;
	private String filtro;
	private List<Pessoa> listaPaciente;
	private Telefone telefone = new Telefone();

	public void pesquisar() {
		PacienteRepository repo = new PacienteRepository();
		listaPaciente = repo.findByNome(getFiltro());
	}
	
	@Override
	public void salvar() {
		PacienteRepository r = new PacienteRepository();
		try {
			r.beginTransaction();
			getEntity().setSenha(Util.hashSHA256(getEntity().getSenha()));
			if (getTelefone() != null) {
				getEntity().getTelefone().add(telefone);
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
	public Pessoa getEntity() {
		if (entity == null)
			entity = new Pessoa();
			if(entity.getPaciente() == null) {
				entity.setPaciente(new Paciente());
				entity.setTelefone(new ArrayList<Telefone>());
			}
		return entity;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public void setListaPaciente(List<Pessoa> listaPaciente) {
		this.listaPaciente = listaPaciente;
	}

	public List<Pessoa> getListaPaciente() {
		if (listaPaciente == null)
			listaPaciente = new ArrayList<Pessoa>();
		return listaPaciente;
	}

	public void abrirPacienteListing() {
		PacienteListing listing = new PacienteListing();
		listing.open();
	}
	
	public void obterPacienteListing(SelectEvent event) {
		Pessoa entity = (Pessoa) event.getObject();
		setEntity(entity);
	}
}
