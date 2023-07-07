package com.duke.mfa.poc.utils;

/**
 * Contains all the endpoints
 * 
 * @author Kazi
 */
public interface Endpoints {

    String BASE = "/";
    String HOME = "/home";
    String HEALTH = "/health";
    String LOGIN = "/login";
    String API = "/api";
    String MFA = "/mfa";
    String REGISTER_USER = "/registerUser";
    String REGISTER_TOTP = "/registerTotp";
    String GET_QR_CODE = "/getQRCode";
    String UPDATE_MFA = "/updateMfa";
    String AUTHENTICATE_MFA = "/verifyTotp";
    String BOOKS = "/books";
    String GET_BOOKS = "/getBooks";
    String CHAIRS = "/chairs";
    String GET_CHAIRS = "/getChairs";
}
