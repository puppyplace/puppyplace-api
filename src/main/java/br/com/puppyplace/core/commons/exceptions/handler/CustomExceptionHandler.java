package br.com.puppyplace.core.commons.exceptions.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import br.com.puppyplace.core.commons.exceptions.ResourceAlreadyInUseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.puppyplace.core.commons.exceptions.BusinessException;
import br.com.puppyplace.core.commons.exceptions.EmailUnavailableException;
import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.commons.exceptions.dto.ErrorDTO;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {

		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		var messages = constraintViolations.stream().map(c -> {
			var sb = new StringBuilder();
			sb.append(c.getPropertyPath());
			sb.append(" ");
			sb.append(c.getMessage());
			return sb.toString();
		}).collect(Collectors.toList());
		var body = ErrorDTO.builder().messages(messages).statusCode(HttpStatus.BAD_REQUEST.toString()).build();

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex) {

		var requiredType = ex.getRequiredType();
		var simpleName = requiredType != null ? requiredType.getSimpleName() : "";

		var sb = new StringBuilder();
			sb.append(ex.getName());
			sb.append(" should be of type ");
			sb.append(simpleName);

		var body = ErrorDTO.builder().message(sb.toString()).statusCode(HttpStatus.BAD_REQUEST.toString()).build();

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		var body = ErrorDTO.builder().messages(errors).statusCode(HttpStatus.BAD_REQUEST.toString()).build();

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({EmailUnavailableException.class, ResourceNotFoundException.class, BusinessException.class, ResourceAlreadyInUseException.class})
	public ResponseEntity<ErrorDTO> handleRegraDeNegocioException(RuntimeException ex) {
		var erroBody = ErrorDTO.builder().message(ex.getMessage()).statusCode(HttpStatus.BAD_REQUEST.toString())
				.build();

		return new ResponseEntity<>(erroBody, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
				var erroBody = ErrorDTO.builder().message(ex.getMessage()).statusCode(HttpStatus.BAD_REQUEST.toString())
				.build();

		return new ResponseEntity<>(erroBody, HttpStatus.BAD_REQUEST);
	}

}
