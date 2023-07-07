package com.duke.mfa.poc.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.duke.mfa.poc.dto.ResponseDto;

/**
 * @author Kazi
 */
@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonAdvice {

    @ExceptionHandler(value = ResponseStatusException.class)
    protected ResponseEntity<ResponseDto> handleError(ResponseStatusException ex, WebRequest request) {
        String message = ex.getReason();
        ResponseDto response = new ResponseDto(message);
        return new ResponseEntity<ResponseDto>(response, ex.getStatusCode());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    protected ResponseEntity<ResponseDto> handleInvalidPassword(BadCredentialsException ex, WebRequest request) {
        String message = ex.getMessage();
        ResponseDto response = new ResponseDto(message);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.UNAUTHORIZED);
    }
}
