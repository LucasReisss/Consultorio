package com.consultorio.repository;

import java.util.List;

import javax.persistence.Query;

import com.consultorio.model.Medico;
import com.consultorio.model.Pessoa;

public class MedicoRepository extends Repository<Pessoa> {

	public List<Medico> findByNome(String nome) {

		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" m ");
		jpql.append("FROM ");
		jpql.append("  Medico m ");
		jpql.append("WHERE ");
		jpql.append(" upper(m.nome) like upper(:nome)");

		Query query = getEntityManager().createQuery(jpql.toString());

		query.setParameter("nome", "%" + nome + "%");

		return query.getResultList();
	}

	public boolean contains(Integer id, String email) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" count(*) ");
		jpql.append("FROM ");
		jpql.append("  Medico m ");
		jpql.append("WHERE ");
		jpql.append(" upper(m.email) = upper(?) ");
		jpql.append(" AND m.id <> ? ");

		Query query = getEntityManager().createNativeQuery(jpql.toString());

		query.setParameter(1, email);
		query.setParameter(2, id == null ? -1 : id);
		
		long resultado = (long) query.getSingleResult();
		
		return resultado == 0 ? false : true;

	}
	
	public boolean contains(String cpf, Integer id) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" count(*) ");
		jpql.append("FROM ");
		jpql.append("  Medico m ");
		jpql.append("WHERE ");
		jpql.append(" upper(m.cpf) = upper(?) ");
		jpql.append(" AND m.id <> ? ");

		Query query = getEntityManager().createNativeQuery(jpql.toString());

		query.setParameter(1, cpf);
		query.setParameter(2, id == null ? -1 : id);
		
		long resultado = (long) query.getSingleResult();
		
		return resultado == 0 ? false : true;
	}

}
