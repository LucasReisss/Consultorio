package com.consultorio.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.Util;
import com.consultorio.application.ValidationException;
import com.consultorio.model.Agenda;
import com.consultorio.model.Endereco;
import com.consultorio.model.Medico;
import com.consultorio.model.Paciente;
import com.consultorio.model.Pessoa;
import com.consultorio.model.Telefone;
import com.consultorio.repository.AgendaRepository;

@Named
@ViewScoped
public class AgendaController extends Controller<Pessoa> {

	private static final long serialVersionUID = 4948245614997659993L;

	private Agenda agenda = new Agenda();
	private List<Agenda> listaAgenda = new ArrayList<Agenda>();
	private ScheduleModel agendas = new DefaultScheduleModel();;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private int idAgenda;
	private List<Paciente> pacientes = new ArrayList<Paciente>();
	private Pessoa paciente = new Pessoa();
	private Pessoa medico = new Pessoa();
	private List<Medico> medicos = new ArrayList<Medico>();

	public void listar() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		AgendaRepository repo = new AgendaRepository();
		agendas = new DefaultScheduleModel();

		if (pessoa.getPaciente() != null) {
			listaAgenda = repo.listarAgendaPacientePorId(pessoa.getId());
			if (listaAgenda.isEmpty()) {
				if (pessoa.getMedico() != null) {
					listaAgenda = repo.listarAgendaMedPorId(pessoa.getId());
					if (listaAgenda.isEmpty()) {
						Util.addMessageInfo("Você não tem nada agendado.");
						return;
					} else {
						for (Agenda agenda : listaAgenda) {
							DefaultScheduleEvent evt = new DefaultScheduleEvent();
							// add id para recuperar dps
							evt.setDescription(agenda.getId().toString());
							evt.setData(agenda.getAtendimento());
							LocalDateTime ldt = LocalDateTime.ofInstant(agenda.getAtendimento().toInstant(),
									ZoneId.systemDefault());
							evt.setStartDate(ldt);
							evt.setEndDate(ldt);

							evt.setTitle(agenda.getNome());
							evt.setAllDay(false);
							evt.setEditable(true);
							agendas.addEvent(evt);
						}
						return;
					}
				} else {
					Util.addMessageInfo("Você não tem nada agendado.");
					return;
				}
			} else {
				for (Agenda agenda : listaAgenda) {
					DefaultScheduleEvent evt = new DefaultScheduleEvent();
					// add id para recuperar dps
					evt.setDescription(agenda.getId().toString());
					evt.setData(agenda.getAtendimento());
					LocalDateTime ldt = LocalDateTime.ofInstant(agenda.getAtendimento().toInstant(),
							ZoneId.systemDefault());
					evt.setStartDate(ldt);
					evt.setEndDate(ldt);

					evt.setTitle(agenda.getNome());
					evt.setAllDay(false);
					evt.setEditable(true);
					agendas.addEvent(evt);
				}
				return;
			}

		} else if (pessoa.getMedico() != null) { // verificando se o med tem agenda
			listaAgenda = repo.listarAgendaMedPorId(pessoa.getId());
			if (listaAgenda.isEmpty()) {
				Util.addMessageInfo("Você não tem nada agendado.");
				return;
			} else {
				for (Agenda agenda : listaAgenda) {
					DefaultScheduleEvent evt = new DefaultScheduleEvent();
					// add id para recuperar dps
					evt.setDescription(agenda.getId().toString());
					evt.setData(agenda.getAtendimento());
					LocalDateTime ldt = LocalDateTime.ofInstant(agenda.getAtendimento().toInstant(),
							ZoneId.systemDefault());
					evt.setStartDate(ldt);
					evt.setEndDate(ldt);

					evt.setTitle(agenda.getNome());
					evt.setAllDay(false);
					evt.setEditable(true);
					agendas.addEvent(evt);
				}
			}
		}

		else {
			Util.addMessageInfo("Cadastre-se no nosso site.");
		}

	}

	public void listarAgendaMedSelecionado(Integer id) {
		agendas = new DefaultScheduleModel();
		limpar();
		editar(id);
		AgendaRepository repo = new AgendaRepository();

		listaAgenda = repo.listarAgendaMedPorId(getEntity().getId());
		if (listaAgenda.isEmpty()) {
			Util.addMessageInfo(getEntity().getNome() + " não tem nada agendado.");
			return;
		} else {
			for (Agenda agenda : listaAgenda) {
				DefaultScheduleEvent evt = new DefaultScheduleEvent();
				// add id para recuperar dps
				evt.setDescription(agenda.getId().toString());
				evt.setData(agenda.getAtendimento());
				LocalDateTime ldt = LocalDateTime.ofInstant(agenda.getAtendimento().toInstant(),
						ZoneId.systemDefault());
				evt.setStartDate(ldt);
				evt.setEndDate(ldt);

				evt.setTitle(agenda.getNome());
				evt.setAllDay(false);
				evt.setEditable(true);
				agendas.addEvent(evt);
			}
		}
	}

	public void eventoSelecionado(SelectEvent evento) {
		limpar();
		ScheduleEvent event = (ScheduleEvent) evento.getObject();

		for (Agenda agenda : listaAgenda) {
			if (agenda.getId().toString().equals(event.getDescription())) {
				AgendaRepository repo = new AgendaRepository();
				idAgenda = Integer.parseInt(event.getDescription());
				medico = repo.pegarMedicoPorAgendaId(idAgenda);
				paciente = repo.pegarPacientePorAgendaId(idAgenda);

				this.agenda = agenda;
				break;
			}
		}
	}

	@Override
	public void salvar() {
		if (agenda.getId() == null) {
			Util.addMessageWarn("Espere o paciente fazer o agendamento.");
		} else {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
			editar(pessoa.getId());

			for (int i = 0; i < listaAgenda.size(); i++) {
				if (getEntity().getPaciente().getAgenda().get(i).getId().equals(agenda.getId())) {
					getEntity().getPaciente().getAgenda().get(i).setNome(agenda.getNome());
				}
			}
			AgendaRepository r = new AgendaRepository();

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
			Util.addMessageInfo("Descrição Alterada com sucesso");
			limpar();
			listar();
		}
	}

	public Agenda selDescEData(Integer id) throws ValidationException {
		AgendaRepository repo = new AgendaRepository();
		if (agenda.getNome().isBlank() || agenda.getAtendimento() == null) {
			Util.addMessageError("Preencha todos os campos");
			return agenda = new Agenda();
		} else {
			LocalDate data = new java.sql.Date(agenda.getAtendimento().getTime()).toLocalDate();
			LocalDate hoje = LocalDate.now();
			LocalTime tarde = LocalTime.parse("18:00");
			LocalTime preAlmoco = LocalTime.parse("11:00");
			LocalTime posAlmoco = LocalTime.parse("14:00");
			LocalTime manha = LocalTime.parse("08:00");
			if (data.isBefore(hoje)) {
				Util.addMessageError("Data inválida");
				return agenda = new Agenda();
			} else if (!repo.listarAgendaMedPorId(id).isEmpty() || repo.listarAgendaMedPorId(id).isEmpty()) {
				List<Agenda> agendas = new ArrayList<Agenda>();
				agendas = repo.listarAgendaMedPorId(id);
				LocalDateTime ldt = LocalDateTime.ofInstant(agenda.getAtendimento().toInstant(),
						ZoneId.systemDefault());
				if (ldt.getMinute() != 00 && ldt.getMinute() != 30) {
					Util.addMessageWarn("Hora de atendimento inválida. Padrão de 30 em 30 minutos");
					return agenda = new Agenda();

				}
				if (ldt.toLocalTime().isAfter(manha) && ldt.toLocalTime().isBefore(preAlmoco)) {
					for (int i = 0; i < agendas.size(); i++) {
						LocalDateTime agendaLista = LocalDateTime.ofInstant(agendas.get(i).getAtendimento().toInstant(),
								ZoneId.systemDefault());
						if (ldt.equals(agendaLista)) {
							Util.addMessageError("Data da consulta já existente");
							agenda = new Agenda();
							break;
						}
					}

					return agenda;
				} else if (ldt.toLocalTime().isAfter(posAlmoco) && ldt.toLocalTime().isBefore(tarde)) {
					for (int i = 0; i < agendas.size(); i++) {
						LocalDateTime agendaLista = LocalDateTime.ofInstant(agendas.get(i).getAtendimento().toInstant(),
								ZoneId.systemDefault());
						if (ldt.equals(agendaLista)) {
							Util.addMessageError("Data da consulta já existente");
							agenda = new Agenda();
							break;
						}
					}
				} else {
					Util.addMessageWarn("Horários disponíveis: 8:30-10:30 / 14:30-17:30");
					return agenda = new Agenda();
				}

			}

		}
		return agenda;
	}

	public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
		limpar();
		event = selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
		limpar();
		event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject())
				.endDate(selectEvent.getObject().plusHours(1)).build();
		// transformando o local date time em Date
		ZonedDateTime zdt = selectEvent.getObject().atZone(ZoneId.systemDefault());
		agenda.setAtendimento(Date.from(zdt.toInstant()));
	}

	public void limpar() {
		agenda = new Agenda();
		paciente = new Pessoa();
		medico = new Pessoa();
	}

	@Override
	public Pessoa getEntity() {
		if (entity == null) {
			entity = new Pessoa();
			entity.setTelefone(new ArrayList<Telefone>());
			entity.setEndereco(new Endereco());
		}

		return entity;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public List<Agenda> getListaAgenda() {
		return listaAgenda;
	}

	public void setListaAgenda(List<Agenda> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}

	public ScheduleModel getAgendas() {
		return agendas;
	}

	public void setAgendas(ScheduleModel agendas) {
		this.agendas = agendas;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public int getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(int idAgenda) {
		this.idAgenda = idAgenda;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public Pessoa getPaciente() {
		return paciente;
	}

	public void setPaciente(Pessoa paciente) {
		this.paciente = paciente;
	}

	public Pessoa getMedico() {
		return medico;
	}

	public void setMedico(Pessoa medico) {
		this.medico = medico;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

}
