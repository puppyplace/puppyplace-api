package br.com.puppyplace.core.commons.exceptions;

public class ResourceAlreadyInUseException extends RuntimeException {
    private static final long serialVersionUID = 4163295951428433073L;

    public ResourceAlreadyInUseException(){
        super();
    }
    
    public ResourceAlreadyInUseException(String message){
        super(message);
    }
}
