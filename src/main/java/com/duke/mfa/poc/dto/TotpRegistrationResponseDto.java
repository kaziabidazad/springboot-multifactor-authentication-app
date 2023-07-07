package com.duke.mfa.poc.dto;

/**
 * @author Kazi
 */
public class TotpRegistrationResponseDto implements CommonResponseDto {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TotpRegistrationResponseDto [message=" + message + "]";
    }

}
