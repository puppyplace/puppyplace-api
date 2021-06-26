package br.com.puppyplace.core.commons.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1070785737975395462L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
