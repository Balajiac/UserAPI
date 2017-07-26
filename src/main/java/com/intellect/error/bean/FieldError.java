package com.intellect.error.bean;

/**
 * To hold a field's error details
 * 
 * @author Balaji, Chandrasekaran
 *
 */
public class FieldError {

	private String field;

	private String message;

	public FieldError(String field, String message) {
		this.field = field;
		this.message = message;
	}

}
