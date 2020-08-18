package com.consultorio.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.Util;
import com.consultorio.application.ValidationException;
import com.consultorio.factory.JPAFactory;
import com.consultorio.listing.PacienteListing;
import com.consultorio.model.Agenda;
import com.consultorio.model.Atendimento;
import com.consultorio.model.EspecialidadeMedica;
import com.consultorio.model.Paciente;
import com.consultorio.model.Pessoa;
import com.consultorio.model.Telefone;
import com.consultorio.repository.MedicoRepository;
import com.consultorio.repository.PacienteRepository;

@Named
@ViewScoped
public class PacienteController extends Controller<Pessoa> {

	private static final long serialVersionUID = -7996231487557010298L;
	private String filtro;
	private Agenda agenda = new Agenda();
	private AgendaController agendaController = new AgendaController();
	private List<Pessoa> listaPaciente;
	private List<Pessoa> listaMedico;
	private Pessoa medico = new Pessoa();
	private EspecialidadeMedica esp = new EspecialidadeMedica();
	private List<EspecialidadeMedica> listaEspecialide = new ArrayList<EspecialidadeMedica>();
	private Atendimento atendimento = new Atendimento();
	private List<Agenda> agendas = new ArrayList<Agenda>();
	private Telefone telefone = new Telefone();
	private List<Pessoa> lista;
	private boolean validacao = false;
	private boolean medSelecionado = false;

	public void pesquisarMed() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());

		if (getEntity().getMedico() != null) {
			MedicoRepository repo = new MedicoRepository();
			listaMedico = repo.findOthersByNome(getFiltro(), getEntity().getId());
		} else {
			MedicoRepository repo = new MedicoRepository();
			listaMedico = repo.findByNome(getFiltro());
		}
	}

	public void editarMed(int id) {
		EntityManager em = JPAFactory.getEntityManager();
		setMedico((Pessoa) em.find(getEntity().getClass(), id));

		medSelecionado = false;

	}

	public void pesquisar() {
		PacienteRepository repo = new PacienteRepository();
		listaPaciente = repo.findByNome(getFiltro());
	}

	public void pesquisarAgenda() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());
		if (getEntity().getPaciente() != null) {
			PacienteRepository repo = new PacienteRepository();
			agendas = repo.pesquisarAgenda(getEntity().getPaciente().getId());
		} else {
			agendas = new ArrayList<Agenda>();
		}
	}

	public String retornarData() {

		Date data = new Date();

		String dia = "10";
		String mes = "08";
		String ano = "2020";

		Locale localeBR = new Locale("pt", "BR");

		SimpleDateFormat fmt = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", localeBR);

		return fmt.format(new Date());
	}

	public String temExame() {
		pesquisarAgenda();
		Integer num = 0;
		if (agendas.isEmpty()) {
			return "Você ainda não tem nenhum exame marcado..";
		} else {
			LocalDate hoje = LocalDate.now();
			for (int i = 0; i < agendas.size(); i++) {
				LocalDate data = new java.sql.Date(agendas.get(i).getAtendimento().getTime()).toLocalDate();
				if (data.isAfter(hoje)) {
					num ++;
				}
			}
			
			return "Atualmente você tem " + num.toString() + " exame(s) em aberto";
		}
	}

	public void salvarExame() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		super.editar(pessoa.getId());

		if (getEntity().getPaciente().getAtendimento() == null) {
			getEntity().getPaciente().setAtendimento(new ArrayList<Atendimento>());
		}

		LocalDateTime ag = LocalDateTime.ofInstant(agenda.getAtendimento().toInstant(), ZoneId.systemDefault());
		boolean boleano = false;

		for (int i = 0; i < getEntity().getPaciente().getAgenda().size(); i++) {
			LocalDateTime agendas = LocalDateTime.ofInstant(
					getEntity().getPaciente().getAgenda().get(i).getAtendimento().toInstant(), ZoneId.systemDefault());
			if (ag.equals(agendas)) {
				boleano = true;
				break;
			}
		}
		
		atendimento.setData(agenda.getAtendimento());
		getEntity().getPaciente().getAtendimento().add(atendimento);

		agenda.setMedico(getMedico().getMedico());
		agenda.setPaciente(getEntity().getPaciente());
		
		// add o agendamento tanto p/ o med quanto para o paciente
		getMedico().getMedico().getAgenda().add(agenda);
		getEntity().getPaciente().getAgenda().add(agenda);

		if (boleano == true) {
			Util.addMessageError("Você já tem um agendamento para esse dia e horário");
			limpar();
			agenda = new Agenda();
		} else {
			PacienteRepository r = new PacienteRepository();
			try {
				r.beginTransaction();
				r.salvar(getEntity());
				r.salvar(getMedico());
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
			Util.addMessageInfo("Exame cadastrado com sucesso.");
			agenda = new Agenda();
		}
	}

	public void carregarAgendaDoMed() {
		agendaController.listarAgendaMedSelecionado(medico.getId());
		medSelecionado = true;
	}

	public void verificarDadosConsulta(Integer id) throws ValidationException {
		editarMed(id);
		agenda = agendaController.selDescEData(medico.getId());
		if (agenda.getNome() == null || agenda.getNome().isBlank()) {
			validacao = false;
		} else {
			validacao = true;
		}
	}

	public void validarFalso() {
		validacao = false;
	}

	@Override
	public void limpar() {
		medico = new Pessoa();
		listaEspecialide = new ArrayList<EspecialidadeMedica>();
		agendaController.limpar();
		validacao = false;
	}

	public void limparExame() {
		atendimento = new Atendimento();
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
		if (entity == null) {
			entity = new Pessoa();
			if (entity.getPaciente() == null) {
				entity.setPaciente(new Paciente());
				entity.setTelefone(new ArrayList<Telefone>());
			}
		}

		return entity;
	}

	public List<Pessoa> espeMedSel(Integer id) {
		lista = new ArrayList<Pessoa>();
		MedicoRepository repo = new MedicoRepository();
		try {
			lista = repo.findByEspecialidade(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Util.addMessageError(e.getMessage());
		}
		return lista;
	}

	public List<Pessoa> retornarEspecialidadesPorId(Integer id) {
		lista = new ArrayList<Pessoa>();
		MedicoRepository repo = new MedicoRepository();
		try {
			lista = repo.findByEspecialidade(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Util.addMessageError(e.getMessage());
		}
		return lista;
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

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

	public Atendimento getExame() {
		return atendimento;
	}

	public void setExame(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public List<Pessoa> getListaMedico() {
		return listaMedico;
	}

	public void setListaMedico(List<Pessoa> listaMedico) {
		this.listaMedico = listaMedico;
	}

	public Pessoa getMedico() {
		return medico;
	}

	public void setMedico(Pessoa medico) {
		this.medico = medico;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public List<Pessoa> getLista() {
		return lista;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}

	public EspecialidadeMedica getEsp() {
		return esp;
	}

	public void setEsp(EspecialidadeMedica esp) {
		this.esp = esp;
	}

	public List<EspecialidadeMedica> getListaEspecialide() {
		return listaEspecialide;
	}

	public void setListaEspecialide(List<EspecialidadeMedica> listaEspecialide) {
		this.listaEspecialide = listaEspecialide;
	}

	public AgendaController getAgendaController() {
		return agendaController;
	}

	public void setAgendaController(AgendaController agendaController) {
		this.agendaController = agendaController;
	}

	public boolean isValidacao() {
		return validacao;
	}

	public void setValidacao(boolean validacao) {
		this.validacao = validacao;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public boolean isMedSelecionado() {
		return medSelecionado;
	}

	public void setMedSelecionado(boolean medSelecionado) {
		this.medSelecionado = medSelecionado;
	}	

}
