package com.github.radium226.vianavigo;

public class NoLocationFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoLocationFoundException() {
		super();
	}

	public NoLocationFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoLocationFoundException(String message) {
		super(message);
	}

	public NoLocationFoundException(Throwable cause) {
		super(cause);
	}

}
