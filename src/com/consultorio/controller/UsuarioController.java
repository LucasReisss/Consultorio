package com.consultorio.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.Util;
import com.consultorio.application.ValidationException;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.Endereco;
import com.consultorio.model.EspecialidadeMedica;
import com.consultorio.model.Medico;
import com.consultorio.model.Pessoa;
import com.consultorio.model.Telefone;
import com.consultorio.repository.EspecialidadeMedicaRepository;
import com.consultorio.repository.MedicoRepository;
import com.consultorio.repository.UsuarioRepository;
import com.google.gson.Gson;

@Named
@ViewScoped
public class UsuarioController extends Controller<Pessoa> {

	private static final long serialVersionUID = -1879380176732858321L;
	
	private String filtro;
	private List<Pessoa> listaUsuario;
	private List<Telefone> listaTelefones;
	private Telefone telefone = new Telefone();
	private Endereco endereco = new Endereco();
	private EspecialidadeMedica especialidadeMedica;
	private List<EspecialidadeMedica> listaEspecialidade;
	private List<EspecialidadeMedica> especialidades;
	private List<Pessoa> lista;
	
	
	public Pessoa logar(String email, String senha) {
		EntityManager em = JPAFactory.getEntityManager();
		System.out.println("entrou aqui");
		try {
			
			Pessoa user =  (Pessoa) em.createNamedQuery("logar", Pessoa.class).setParameter("email", email)
					.setParameter("senha", senha).getSingleResult();
			
			if (user != null) {
				return user;
			}
			return user;
		} catch (NoResultException e) {
			Pessoa user = null;
			return user;
		}
	}
	
//	public void pesquisar() {
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
//		super.editar(pessoa.getId());
//		UsuarioRepository repo = new UsuarioRepository();
//		listaUsuario = repo.findByNome(getEntity().getId(), getFiltro());
//	}

	
	@Override
	public void editar(int id) {
		super.editar(id);
	}
	
	@Override
	public void salvar() {
		UsuarioRepository r = new UsuarioRepository();
		try {
			r.beginTransaction();
			getEntity().setSenha(Util.hashSHA256(getEntity().getSenha()));
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
	
//  								TELEFONE  
	
	public void pesquisarTelefone() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		UsuarioRepository repo = new UsuarioRepository();
		listaTelefones = repo.findByNumero(getEntity().getId(), getFiltro());
	}
	
	public List<Telefone> listaTelefone() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		UsuarioRepository repo = new UsuarioRepository();
		listaTelefones = repo.findTelefonesById(getEntity().getId());
		
		return listaTelefones;
	}
	
	public void editarTelefone(Integer idTelefone) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		UsuarioRepository repo = new UsuarioRepository();
		telefone = repo.editarTelefone(getEntity().getId(), idTelefone);
	}
	
	public void excluirTel(Integer idTel) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		int auxTelefone = 0;
		for (int i = 0; i < getEntity().getTelefone().size(); i++) {
			if (getEntity().getTelefone().get(i).getId() == idTel) {
				auxTelefone = i;
			}
		}
		
		UsuarioRepository r = new UsuarioRepository();
		try {
			r.beginTransaction();
			r.excluir(getEntity().getTelefone().get(auxTelefone));
			r.commitTransaction();
			pesquisarTelefone();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void limparTel() {
		telefone = new Telefone();
	}
	
	public void salvarTelefone() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		UsuarioRepository r = new UsuarioRepository();
		listaTelefones = r.existeTelefone(telefone.getDdd(), telefone.getNumero());
		if (listaTelefones.size() > 0) {
			Util.addMessageError("Este telefone já existe.");
		} else {
			try {
				r.beginTransaction();
				if (getEntity().getTelefone() == null) {
					getEntity().setTelefone(new ArrayList<Telefone>());
					getEntity().getTelefone().add(telefone);

				}
				if (getEntity().getTelefone() != null) {
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
			limparTel();
			Util.addMessageInfo("Telefone Adicionado com sucesso.");
		}
		
	}
	
	
//                   			  ENDERECO                             
	
	public void pesquisarCep(AjaxBehaviorEvent event) {
		
		try {
	
			URL url = new URL("https://viacep.com.br/ws/" + endereco.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}

			Endereco end = new Gson().fromJson(jsonCep.toString(), Endereco.class);

			if (end.getCep() == null || end.getCep() == "") {
				Util.addMessageWarn("Cep inválido");
				limpar();
				limparEnd();
			} else {
				endereco.setCep(end.getCep());
				endereco.setLogradouro(end.getLogradouro());
				endereco.setComplemento(end.getComplemento());
				endereco.setBairro(end.getBairro());
				endereco.setLocalidade(end.getLocalidade());
				endereco.setUf(end.getUf());
			}
			
			
		} catch (Exception e) {
			Util.addMessageWarn("Cep inválido");
			limpar();
			limparEnd();
			return;
		}
		
	}
	
	public void salvarEndereco() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		UsuarioRepository r = new UsuarioRepository();
		try {
			r.beginTransaction();
			if (endereco.getUf() != null) {
				getEntity().setEndereco(endereco);
			} else {
				Util.addMessageWarn("Existem campos em branco");
				return;
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
		limparEnd();
		Util.addMessageInfo("Endereço Adicionado com sucesso.");
	}
	
	public boolean botaoSalvarEnd() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		if(getEntity().getEndereco() == null) {
			return false;
		} 
		else if (getEntity().getEndereco().getUf() == null) {
			return false;
		}
		else {
			if (getEntity().getEndereco().getId() != null && endereco.getId() != null) {
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	public void editarEndereco() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		UsuarioRepository repo = new UsuarioRepository();
		endereco = repo.editarEndereco(pessoa.getId(), getEntity().getEndereco().getId());

	}
	
	public void limparEnd() {
		endereco = new Endereco();
	}
	
	public boolean botaoEditarEnd() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		if(getEntity().getEndereco() == null) {
			return true;
		}
		else if (getEntity().getEndereco().getUf() == null) {
			return true;
		}
		else {
			if (getEntity().getEndereco().getId() != null && endereco.getId() != null) {
				return true;
			} else {
				return false;				
			}
		}
	}
	
//	public void abrirUsuarioListing() {
//		UsuarioListing listing = new UsuarioListing();
//		listing.open();
//	}
//	
//	public void obterUsuarioListing(SelectEvent event) {
//		Usuario entity = (Usuario) event.getObject();
//		getEntity().setUsuario(entity);
//	}

	@Override
	public Pessoa getEntity() {
		if(entity == null) {
			entity = new Pessoa();
			entity.setTelefone(new ArrayList<Telefone>());
			entity.setEndereco(new Endereco());
		}
		return entity;
	}
	
	// 										PACIENTE
	
	
	//                      				MEDICO
	
	public void salvarEspecialidade() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		if (getEntity().getMedico() == null) {
			getEntity().setMedico(new Medico());
		}
		
		mandarSelecionados();
		
		if (!listaEspecialidade.isEmpty()) {
			getEntity().getMedico().setListaEspecialidade(listaEspecialidade);
			UsuarioRepository r = new UsuarioRepository();
			try {
				r.beginTransaction();
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
			Util.addMessageInfo("Especialiade cadastrada com sucesso.");
		} else {
			Util.addMessageWarn("Nenhuma especialidade selcionada");				
		}
	}
	
	public boolean botaoSalvarEsp() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		if(getEntity().getMedico() == null) {
			return false;
		} 
		else {
			if (getEntity().getMedico().getListaEspecialidade().isEmpty()) {
				return false;
			}
			else {
				return true;
			}
		}
		
	}
	
	public boolean botaoEditarEsp() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		if(getEntity().getMedico() == null) {
			return true;
		}
		else {
			if (getEntity().getMedico().getId() != null && 
					!getEntity().getMedico().getListaEspecialidade().isEmpty()) {
				return false;
			} else {
				return true;				
			}
		}
		
	}
	
	public List<EspecialidadeMedica> gerarEspecialidade() {
		try {
			especialidades = new ArrayList<EspecialidadeMedica>();
			EspecialidadeMedicaRepository repo = new EspecialidadeMedicaRepository();
			especialidades = repo.findAll();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Util.addMessageError(e.getMessage());
		}
		return especialidades;
	}
	
	public List<EspecialidadeMedica> mandarSelecionados() {
		
		List<EspecialidadeMedica> cloneEspecialidade = new ArrayList<EspecialidadeMedica>();
		
			for (int j = 0; j < listaEspecialidade.size(); j++) {
				for (int i = 0; i < especialidades.size(); i++) {
				if (especialidades.get(i).getId().equals(listaEspecialidade.get(j))) {
					cloneEspecialidade.add(especialidades.get(i));
				}
			}
				gerarEspecialidade();
		}

		listaEspecialidade = cloneEspecialidade;
		

		return listaEspecialidade;
	}
	
	public List<Pessoa> retornarEspecialidadesPorId() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		lista = new ArrayList<Pessoa>();
		MedicoRepository repo = new MedicoRepository();
		try {
			lista = repo.findByEspecialidade(getEntity().getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Util.addMessageError(e.getMessage());
		}
		return lista;
	}
	
	public void limparEsp() {
		especialidadeMedica = new EspecialidadeMedica();
	}
	
	public List<EspecialidadeMedica> pesquisarEsp() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		if (getEntity().getMedico() != null) {
			if (getEntity().getMedico().getListaEspecialidade() != null) {
				UsuarioRepository repo = new UsuarioRepository();
				try {
					listaEspecialidade = repo.findByEspecialidade(getEntity().getId());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					Util.addMessageError(e.getMessage());
				}
			}
		}
		
		return listaEspecialidade;
	}
	
	public void excluirEsp(Integer id) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		
		for (int i = 0; i < getEntity().getMedico().getListaEspecialidade().size(); i++) {
			if (getEntity().getMedico().getListaEspecialidade().get(i).getId() == id) {
				getEntity().getMedico().getListaEspecialidade().remove(i);
			}
		}
		
		UsuarioRepository r = new UsuarioRepository();
		try {
			r.beginTransaction();
			try {
				r.salvar(getEntity());
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			r.commitTransaction();
			pesquisarEsp();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// 									  GETTERS E SETTERS

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Pessoa> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Pessoa> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public List<Telefone> getListaTelefones() {
		return listaTelefones;
	}

	public void setListaTelefones(List<Telefone> listaTelefones) {
		this.listaTelefones = listaTelefones;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public EspecialidadeMedica getEspecialidadeMedica() {
		return especialidadeMedica;
	}

	public void setEspecialidadeMedica(EspecialidadeMedica especialidadeMedica) {
		this.especialidadeMedica = especialidadeMedica;
	}

	public List<EspecialidadeMedica> getListaEspecialidade() {
		return listaEspecialidade;
	}

	public void setListaEspecialidade(List<EspecialidadeMedica> listaEspecialidade) {
		this.listaEspecialidade = listaEspecialidade;
	}

	public List<EspecialidadeMedica> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<EspecialidadeMedica> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Pessoa> getLista() {
		return lista;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}
	
}
