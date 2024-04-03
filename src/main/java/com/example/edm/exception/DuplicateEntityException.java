package com.example.edm.exception;

public class DuplicateEntityException  extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9087073933831889462L;

	public DuplicateEntityException (String message) {
        super(message);
    }
}
