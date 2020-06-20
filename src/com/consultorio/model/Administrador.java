package com.consultorio.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.consultorio.model.validation.Validation;

@Entity
@DiscriminatorValue("Adm")
public class Administrador extends Pessoa {

	private static final long serialVersionUID = 2164284968159609561L;
	
	
	@Override
	public Validation<Pessoa> getValidation() {
		return super.getValidation();
	}
	

}
