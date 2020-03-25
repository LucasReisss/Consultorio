package com.consultorio.repository;

import javax.persistence.EntityManager;

import com.consultorio.application.RepositoryException;
import com.consultorio.factory.JPAFactory;
import com.consultorio.model.DefaultEntity;

public class Repository <T extends DefaultEntity<T>> {
	private EntityManager entityManager;
	
	public Repository() {
		entityManager = JPAFactory.getEntityManager();
	}
	
	public Repository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void beginTransaction() throws RepositoryException {
		try {
			getEntityManager().getTransaction().begin();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Problema ao "
					+ "iniciar uma transa��o");
		}
	}
	
	public void commitTransaction() throws RepositoryException {
		try {
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepositoryException("Problema ao "
					+ "comitar uma transa��o");
		}
	}
	
	public void rollbackTransaction() {
		try {
			getEntityManager().getTransaction().rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvar(T entity) throws RepositoryException {
		try {
			getEntityManager().merge(entity);
		} catch (Exception e) {
			System.out.println("Erro no repositorio "
					+ "ao executar o m�todo merge.");
			e.printStackTrace();
			throw new RepositoryException("Erro ao salvar.");
		}
	}
	
	public void excluir(T entity) throws RepositoryException {
		try {
			T obj = getEntityManager().merge(entity);
			getEntityManager().remove(obj);
		} catch (Exception e) {
			System.out.println("Erro no repositorio "
					+ "ao executar o m�todo merge.");
			e.printStackTrace();
			throw new RepositoryException("Erro ao salvar.");
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
