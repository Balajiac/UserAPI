package com.intellect.error.bean;

/**
 * To throw a customized Exception 
 * @author Balaji, Chandrasekaran
 *
 */
public class CustomErrorType extends Exception {
	private static final long serialVersionUID = -4768287473162209735L;

	/**
	 * @param message
	 */
	public CustomErrorType(String message) {
		super(message);
	}
}
