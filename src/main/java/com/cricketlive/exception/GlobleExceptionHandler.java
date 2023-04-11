package com.cricketlive.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cricketlive.payload.ApiResponse;

@RestControllerAdvice
public class GlobleExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
    	String message = ex.getMessage();
    	ApiResponse apiResponse = new ApiResponse(message, false);
    	return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String messsage = error.getDefaultMessage();
			resp.put(fieldName, messsage);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
}
