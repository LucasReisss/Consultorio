package com.consultorio.listing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.consultorio.model.Financeiro;
import com.consultorio.model.Pessoa;
import com.consultorio.repository.FinanceiroRepository;

@Named
@ViewScoped
public class FinanceiroListing extends Listing<Financeiro> {

	private static final long serialVersionUID = -5274842308492045809L;
	private List<Financeiro> list;
	private String filtro;
	
	public FinanceiroListing() {
		super(Financeiro.class);
	}

	public void open() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", true);
		options.put("draggable", true);
		options.put("modal", true);
		options.put("width", 800);
		options.put("height", 500);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");

		PrimeFaces.current().dialog().openDynamic("financeirolisting", options, null);
	}

	public void pesquisar() {
		FinanceiroRepository repo = new FinanceiroRepository();
		list = repo.findByNome(getFiltro());
	}

	public List<Financeiro> getList() {
		if (list == null)
			list = new ArrayList<Financeiro>();
		return list;
	}

	public void setList(List<Financeiro> list) {
		this.list = list;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

}
