package controller;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Paciente;

@Named
@RequestScoped
public class HelloController {
	
	// teste do JPA
	public static void main(String[] args) {
		Paciente paciente = new Paciente();
		
		paciente.setNome("Ricardo");
		paciente.setSexo("Masculino");
		paciente.setDataAniversario(new Date());
		paciente.setCpf(00000000002);
		paciente.setIdade(25);
		
		Paciente paciente2 = new Paciente();
		
		paciente2.setNome("Maria");
		paciente2.setSexo("Feminino");
		paciente2.setDataAniversario(new Date());
		paciente2.setCpf(00000000001);
		paciente2.setIdade(32);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Paciente");
		EntityManager em = emf.createEntityManager();
		
		// iniciando uma transaction
		em.getTransaction().begin();
		em.persist(paciente);
		em.persist(paciente2);
		// finalizando a transaction
		em.getTransaction().commit();
		
	}	

}
