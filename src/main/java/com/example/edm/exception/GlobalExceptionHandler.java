package com.example.edm.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ApplicationException.class)
	protected ResponseEntity<ErrorResponse> handleGlobalException(ApplicationException ae) {
		LOGGER.error("Application Business Error Thrown :: {}", ae.getMessage());
		return ResponseEntity.badRequest()
				.body(ErrorResponse.builder().message(ae.getMessage()).timestamp(Instant.now())
						.title("Application Business Error.").status(HttpStatus.BAD_REQUEST.value()).build());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		LOGGER.error("IllegalArgumentException Thrown :: {}", e.getMessage());
		return ResponseEntity.badRequest().body(ErrorResponse.builder().message(e.getMessage()).timestamp(Instant.now())
				.title("Bad Request").status(HttpStatus.BAD_REQUEST.value()).build());
	}

	@ExceptionHandler(DepartmentNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException e) {
		LOGGER.error("Department Not Found Thrown from api :: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message(e.getMessage())
				.timestamp(Instant.now()).title("Department Not Found").status(HttpStatus.NOT_FOUND.value()).build());
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
		LOGGER.error("Employee Not Found Thrown from api :: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message(e.getMessage())
				.timestamp(Instant.now()).title("Employee Not Found").status(HttpStatus.NOT_FOUND.value()).build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
		Map<String, Object> errorsResponse = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		LOGGER.error("Validation Error Thrown :: {}", e.getMessage());
		errorsResponse.put("message", "Validation failed");
		errorsResponse.put("timestamp", Instant.now());
		errorsResponse.put("title", "Validation Error");
		errorsResponse.put("status", HttpStatus.BAD_REQUEST.value());
		errorsResponse.put("errors", errors);
		return ResponseEntity.badRequest().body(errorsResponse);
	}

	@ExceptionHandler(DuplicateEntityException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException e) {
		LOGGER.error("Duplicate entity: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().message(e.getMessage())
				.timestamp(Instant.now()).title("Duplicate Entity").status(HttpStatus.CONFLICT.value()).build());
	}

	@ExceptionHandler(DepartmentDeleteException.class)
	public ResponseEntity<ErrorResponse> handleDepartmentDeleteException(DepartmentDeleteException e) {
		LOGGER.error("Department delete error: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse.builder().message(e.getMessage()).timestamp(Instant.now())
						.title("Department Deletion Error").status(HttpStatus.BAD_REQUEST.value()).build());
	}
}
