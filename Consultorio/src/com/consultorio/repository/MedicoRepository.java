package com.consultorio.repository;

import java.util.List;

import javax.persistence.Query;

import com.consultorio.model.Medico;

public class MedicoRepository extends Repository<Medico> {
	
	public List<Medico> findByNome(String nome){
		
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" m ");
		jpql.append("FROM ");
		jpql.append("  Medico m ");
		jpql.append("WHERE ");
		jpql.append(" upper(a.nome) like upper(:nome)");
		
		Query query = getEntityManager().createQuery(jpql.toString());
		
		query.setParameter("nome", "%" + nome + "%");
		
		return query.getResultList();
	}

}
