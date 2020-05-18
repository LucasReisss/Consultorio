package com.consultorio.model.validation;

import com.consultorio.application.ValidationException;

public interface Validation<T> {
	
	public void validate(T entity) throws ValidationException;
	
}
