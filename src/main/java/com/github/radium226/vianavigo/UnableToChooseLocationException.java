package com.github.radium226.vianavigo;

public class UnableToChooseLocationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnableToChooseLocationException() {
		super();
	}

	public UnableToChooseLocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToChooseLocationException(String message) {
		super(message);
	}

	public UnableToChooseLocationException(Throwable cause) {
		super(cause);
	}

}
