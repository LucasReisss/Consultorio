package com.consultorio.repository;

import java.util.List;

import javax.persistence.Query;

import com.consultorio.model.Agenda;
import com.consultorio.model.Pessoa;

public class AgendaRepository extends Repository<Pessoa>{

	public List<Agenda> listarTodos() {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append("ag ");
		jpql.append("FROM ");
		jpql.append("Pessoa pe ");
		jpql.append("Inner Join Paciente pa on pe.paciente.id = pa.id ");
		jpql.append("Inner Join Agenda ag on pa.agenda.id = ag.id ");

		Query query = getEntityManager().createQuery(jpql.toString());

		return query.getResultList();
	}

	public static void salvar(Agenda agenda) {
		// TODO Auto-generated method stub
		
	}

	public static void remover(Agenda agenda) {
		// TODO Auto-generated method stub
		
	}

}
