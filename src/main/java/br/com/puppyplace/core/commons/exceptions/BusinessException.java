package br.com.puppyplace.core.commons.exceptions;

public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 6389909134053863247L;

    public BusinessException(){
        super();
    }

    public BusinessException(String message){
        super(message);
    }
}
