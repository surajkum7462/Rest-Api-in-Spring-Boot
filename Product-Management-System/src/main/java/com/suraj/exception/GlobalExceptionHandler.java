package com.suraj.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.suraj.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e) {
		
		ErrorResponse error = ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Operation is null").build();
		
		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> handleMissingParameterException(Exception e) {
		
		ErrorResponse error = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build();
		
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	// For validation handler with dependency
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotvalidException(MethodArgumentNotValidException ex)
	{
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String, String> error = new LinkedHashMap<>();
		
		
		allErrors.stream().forEach(er->{
			String message = er.getDefaultMessage();
			String field = ((FieldError) (er)).getField();
			error.put(field, message);
		});
		
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

}
