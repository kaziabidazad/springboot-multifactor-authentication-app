/**
 * 
 */
package com.duke.mfa.poc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kazi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {

    private String message;

    public ResponseDto() {
    }

    /**
     * @param message
     */
    public ResponseDto(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDto [message=" + message + "]";
    }

}
