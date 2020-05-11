package com.consultorio.repository;

import java.util.List;

import javax.persistence.Query;

import com.consultorio.model.EspecialidadeMedica;

public class EspecialidadeMedicaRepository extends Repository<EspecialidadeMedica> {
	
	public List<EspecialidadeMedica> findByNome(String nome){
		
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append("  e ");
		jpql.append("FROM ");
		jpql.append(" EspecialidadeMedico e ");
		jpql.append("WHERE ");
		jpql.append("  upper(e.nome) like upper(:nome) ");
		
		Query query = getEntityManager().createQuery(jpql.toString());
		
		query.setParameter("nome", "%" + nome + "%");
		
		return query.getResultList();
	}
}
