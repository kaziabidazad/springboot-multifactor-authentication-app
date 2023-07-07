package com.duke.mfa.poc.utils;

/**
 * @author Kazi
 */
public interface Constants {

    String APP_NAME = "MFA_APP";
    String TOTP_URL_TEMPLATE = "otpauth://totp/%s:%s?secret=%s&issuer=%s";

}
