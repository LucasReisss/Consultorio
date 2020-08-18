package com.consultorio.repository;

import java.util.List;

import javax.persistence.Query;

import com.consultorio.model.EspecialidadeMedica;
import com.consultorio.model.Financeiro;

public class FinanceiroRepository extends Repository<Financeiro> {
	public List<Financeiro> findAll() {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append("f ");
		jpql.append("FROM ");
		jpql.append("Financeiro f");
		;

		Query query = getEntityManager().createQuery(jpql.toString());

		return query.getResultList();
	}

	public List<Financeiro> findByNome(String nome) {

		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append("  f ");
		jpql.append("FROM ");
		jpql.append(" Financeiro f ");
		jpql.append("WHERE ");
		jpql.append("  upper(f.nome) like upper(:nome) ");

		Query query = getEntityManager().createQuery(jpql.toString());

		query.setParameter("nome", "%" + nome + "%");

		return query.getResultList();
	}

	public Financeiro findById(String id) {
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT ");
		jpql.append("f ");
		jpql.append("FROM ");
		jpql.append("Financeiro f ");
		jpql.append("WHERE ");
		jpql.append("f.id = ? ");

		Query query = getEntityManager().createNativeQuery(jpql.toString());

		query.setParameter(1, id);

		return (Financeiro) query.getSingleResult();
	}
}
