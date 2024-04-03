
package com.example.edm.exception;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public EmployeeNotFoundException(String msg) {
		super(msg);
	}
}
