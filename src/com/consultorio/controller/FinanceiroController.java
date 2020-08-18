package com.consultorio.controller;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.consultorio.listing.FinanceiroListing;
import com.consultorio.model.Financeiro;
import com.consultorio.repository.FinanceiroRepository;

@Named
@ViewScoped
public class FinanceiroController extends Controller<Financeiro> {

	private static final long serialVersionUID = 2878779715380732263L;
	
	private String filtro;
	private List<Financeiro> financeiro;
	
	@Override
	public Financeiro getEntity() {
		if(entity == null) {
			entity = new Financeiro();
		}
		return entity;
	}
	
	public void pesquisar() {
		FinanceiroRepository repo = new FinanceiroRepository();
		financeiro = repo.findByNome(getFiltro());
	}
	
	public void abrirFinanceiroListing() {
		FinanceiroListing listing = new FinanceiroListing();
		listing.open();
	}
	
	public void obterFinanceiroListing(SelectEvent event) {
		Financeiro entity = (Financeiro) event.getObject();
		setEntity(entity);
	}

	
	
	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Financeiro> getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(List<Financeiro> financeiro) {
		this.financeiro = financeiro;
	}
	
	
	
}
