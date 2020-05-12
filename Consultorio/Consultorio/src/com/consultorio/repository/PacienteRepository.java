package com.consultorio.repository;

import java.util.List;

import javax.persistence.Query;

import com.consultorio.model.Paciente;

public class PacienteRepository extends Repository<Paciente> {
	
	public List<Paciente> findByNome(String nome){
		
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" p ");
		jpql.append("FROM ");
		jpql.append("  Paciente p ");
		jpql.append("WHERE ");
		jpql.append(" upper(p.nome) like upper(:nome)");
		
		Query query = getEntityManager().createQuery(jpql.toString());
		
		query.setParameter("nome", "%" + nome + "%");
		
		return query.getResultList();
	}

}
