
package com.example.edm.exception;

public class DepartmentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public DepartmentNotFoundException(String msg) {
		super(msg);
	}
}
