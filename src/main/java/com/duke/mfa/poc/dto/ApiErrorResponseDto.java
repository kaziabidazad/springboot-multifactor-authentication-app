package com.duke.mfa.poc.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * @author Kazi
 */
public class ApiErrorResponseDto extends ResponseDto {

    private HttpStatus status;
    private List<String> errors;

    /**
     * @param message
     * @param status
     * @param errors
     */
    public ApiErrorResponseDto(String message, HttpStatus status, List<String> errors) {
        super(message);
        this.status = status;
        this.errors = errors;
    }

    /**
     * 
     * @param message
     * @param status
     * @param errors
     */
    public ApiErrorResponseDto(String message, HttpStatus status, String errors) {
        super(message);
        this.status = status;
        this.errors = Arrays.asList(errors);
    }

    @Override
    public String toString() {
        return "ApiErrorResponseDto [status=" + status + ", errors=" + errors + "]";
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
