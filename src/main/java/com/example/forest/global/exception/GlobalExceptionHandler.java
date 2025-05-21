package com.example.forest.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
			fieldErrors.put(error.getField(), error.getDefaultMessage())
		);
		
		ErrorResponse response = new ErrorResponse(
			status.name(),
			"잘못된 요청입니다.",
			String.valueOf(status.value()),
			fieldErrors
		);
		return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(ServerException.class)
	public ResponseEntity<ErrorResponse> responseStatusException(ServerException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		String status = errorCode.getStatus().toString();
		String message = errorCode.getMessage();
		String code = errorCode.name();
		ErrorResponse response = new ErrorResponse(status, message, code);
		
		return ResponseEntity.status(errorCode.getStatus()).body(response);
	}
}
