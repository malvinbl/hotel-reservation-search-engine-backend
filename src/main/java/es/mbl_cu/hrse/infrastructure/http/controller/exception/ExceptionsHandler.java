package es.mbl_cu.hrse.infrastructure.http.controller.exception;

import es.mbl_cu.hrse.domain.exception.BadRequestException;
import es.mbl_cu.hrse.domain.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

	private static final Logger log = LogManager.getLogger(ExceptionsHandler.class);
	private static final String ERROR = "error";

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Map<String, Object>> handle(BadRequestException e) {
		log.error("Exception it has happened: ", e);
		var error = e.getMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(Map.of(ERROR, error));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map<String, Object>> handle(NotFoundException e) {
		log.error("Exception it has happened: ", e);
		var error = "Resource not found";
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(Map.of(ERROR, error));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handle(HttpMessageNotReadableException e) {
		log.error("Exception ocurred: ", e);
		var error = "Malformed JSON request";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(Map.of(ERROR, error));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handle(Exception e) {
		log.error("Exception it has happened: ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(Map.of(ERROR, e.getMessage()));
	}

}
