package com.intellect.error.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * to hold all the input fields error validation errors 
 * 
 * @author Balaji, Chandrasekaran
 *
 */
public class ValidationError {

	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	public ValidationError() {

	}

	public void addFieldError(String path, String message) {
		FieldError error = new FieldError(path, message);
		fieldErrors.add(error);
	}

}
