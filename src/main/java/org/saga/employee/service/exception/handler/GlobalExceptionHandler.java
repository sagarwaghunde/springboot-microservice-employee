package org.saga.employee.service.exception.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.saga.employee.service.exception.EmailAlreadyExistsException;
import org.saga.employee.service.exception.ErrorDetails;
import org.saga.employee.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//	@ExceptionHandler(Exception.class)
	@ExceptionHandler
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
																WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), "INTERNAL_SERVER_ERROR");
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
//	@ExceptionHandler(ResourceNotFoundException.class)
	@ExceptionHandler
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
																		WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), "EMPLOYEE_NOT_FOUND");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		
	}

	
//	@ExceptionHandler(EmailAlreadyExistsException.class)
	@ExceptionHandler
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(EmailAlreadyExistsException exception,
																		WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), "EMPLOYEE_EMAIL_ALREADY_EXISTS");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		Map<String, String> errorMap = errorList.stream().collect(Collectors.toMap(error -> ((FieldError)error).getField(), error -> error.getDefaultMessage()));
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);		
	} 
}

