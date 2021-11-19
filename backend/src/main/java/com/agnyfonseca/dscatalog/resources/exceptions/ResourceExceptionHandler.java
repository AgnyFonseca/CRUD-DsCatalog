package com.agnyfonseca.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.agnyfonseca.dscatalog.services.exceptions.DatabaseException;
import com.agnyfonseca.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class) //argumento para saber que tipo de exc. ele vai interceptar
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND; //erro 404 - NOT FOUND
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now()); //pega a data do momento
		err.setStatus(status.value()); 
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI()); //pega o caminho da requisição
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class) //argumento para saber que tipo de exc. ele vai interceptar
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST; //erro 400 - BAD REQUEST
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now()); //pega a data do momento
		err.setStatus(status.value()); 
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI()); //pega o caminho da requisição
		return ResponseEntity.status(status).body(err);
	}
}
