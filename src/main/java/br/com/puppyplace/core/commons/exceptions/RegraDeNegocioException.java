package br.com.puppyplace.core.commons.exceptions;

public class RegraDeNegocioException extends RuntimeException {

	private static final long serialVersionUID = 5115006423246459100L;

	public RegraDeNegocioException() {
		super();
	}
	
	public RegraDeNegocioException(String message) {
		super(message);
	}
	
}
