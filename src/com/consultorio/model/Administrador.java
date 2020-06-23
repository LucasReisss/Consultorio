package com.consultorio.model;

import java.util.GregorianCalendar;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.consultorio.application.RepositoryException;
import com.consultorio.application.ValidationException;
import com.consultorio.model.validation.Validation;
import com.consultorio.repository.AdministradorRepository;

@Entity
@DiscriminatorValue("Adm")
@WebListener
public class Administrador extends Pessoa implements ServletContextListener{

	private static final long serialVersionUID = 2164284968159609561L;
	
	
	@Override
	public Validation<Pessoa> getValidation() {
		return super.getValidation();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		AdministradorRepository repo = new AdministradorRepository();
		Administrador adm = new Administrador();

		adm.setNome("Adm");
		adm.setEmail("adm");
		// senha adm
		adm.setSenha("86f65e28a754e1a71b2df9403615a6c436c32c42a75a10d02813961b86f1e428");
		adm.setDataNascimento(new GregorianCalendar(2000, 01, 01).getTime());

		if (repo.findAll().isEmpty()) {
			try {
				repo.beginTransaction();
				try {
					repo.salvar(adm);
					repo.commitTransaction();
				} catch (ValidationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	

}
