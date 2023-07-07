package com.duke.mfa.poc.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Kazi
 */
public class TotpAuthRequestDto implements CommonRequestDto {

    private String mfaToken;

    /**
     * @param mfaToken
     */
    @JsonCreator
    public TotpAuthRequestDto(String mfaToken) {
        super();
        this.mfaToken = mfaToken;
    }

    public String getMfaToken() {
        return mfaToken;
    }

    @Override
    public String toString() {
        return "TotpAuthRequestDto [mfaToken=" + mfaToken + "]";
    }

}
