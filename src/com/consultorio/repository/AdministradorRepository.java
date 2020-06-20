package com.consultorio.repository;

import java.util.List;

import javax.persistence.Query;

import com.consultorio.model.Administrador;
import com.consultorio.model.Pessoa;

public class AdministradorRepository extends Repository<Pessoa> {

	public List<Administrador> findByNome(String nome) {

		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" a ");
		jpql.append("FROM ");
		jpql.append("  Administrador a ");
		jpql.append("WHERE ");
		jpql.append(" upper(a.nome) like upper(:nome)");

		Query query = getEntityManager().createQuery(jpql.toString());

		query.setParameter("nome", "%" + nome + "%");

		return query.getResultList();
	}

	public boolean containsEmail(Integer id, String email) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" count(*) ");
		jpql.append("FROM ");
		jpql.append("  Pessoa p ");
		jpql.append("WHERE ");
		jpql.append(" upper(p.email) = upper(?) ");
		jpql.append(" AND p.id <> ? ");

		Query query = getEntityManager().createNativeQuery(jpql.toString());

		query.setParameter(1, email);
		query.setParameter(2, id == null ? -1 : id);
		
		long resultado = (long) query.getSingleResult();
		
		return resultado == 0 ? false : true;

	}
	
	public boolean containsCpf(String cpf, Integer id) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" count(*) ");
		jpql.append("FROM ");
		jpql.append("  Pessoa p ");
		jpql.append("WHERE ");
		jpql.append(" upper(p.cpf) = upper(?) ");
		jpql.append(" AND p.id <> ? ");

		Query query = getEntityManager().createNativeQuery(jpql.toString());

		query.setParameter(1, cpf);
		query.setParameter(2, id == null ? -1 : id);
		
		long resultado = (long) query.getSingleResult();
		
		return resultado == 0 ? false : true;
	}
	
	public boolean containsRg(String rg, Integer id) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append(" count(*) ");
		jpql.append("FROM ");
		jpql.append("  Pessoa p ");
		jpql.append("WHERE ");
		jpql.append(" upper(p.rg) = upper(?) ");
		jpql.append(" AND p.id <> ? ");

		Query query = getEntityManager().createNativeQuery(jpql.toString());

		query.setParameter(1, rg);
		query.setParameter(2, id == null ? -1 : id);
		
		long resultado = (long) query.getSingleResult();
		
		return resultado == 0 ? false : true;
	}


}
