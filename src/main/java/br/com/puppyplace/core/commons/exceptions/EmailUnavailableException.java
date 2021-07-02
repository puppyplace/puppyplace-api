package br.com.puppyplace.core.commons.exceptions;

public class EmailUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 5115006423246459100L;

	public EmailUnavailableException() {
		super();
	}
	
	public EmailUnavailableException(String message) {
		super(message);
	}
	
}
