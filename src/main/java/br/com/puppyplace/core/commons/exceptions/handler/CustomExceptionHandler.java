package br.com.puppyplace.core.commons.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.puppyplace.core.commons.exceptions.ErrorModel;
import br.com.puppyplace.core.commons.exceptions.RegraDeNegocioException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		var body = ErrorModel.builder().messages(errors).statusCode(HttpStatus.BAD_REQUEST.toString()).build();

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(RegraDeNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorModel> handleRegraDeNegocioException(RegraDeNegocioException ex){	
		
		var erroBody = ErrorModel.builder()
				.message(ex.getMessage())
				.error_code("5")
				.statusCode(HttpStatus.BAD_REQUEST.toString())
				.build();
		
		return new ResponseEntity<>(erroBody, HttpStatus.BAD_REQUEST);
		
	}

	

}
