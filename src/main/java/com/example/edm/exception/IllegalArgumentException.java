package com.example.edm.exception;

public class IllegalArgumentException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalArgumentException(String message) {
        super(message);
    }
}
