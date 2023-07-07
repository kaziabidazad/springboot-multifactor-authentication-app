package com.duke.mfa.poc.dto;

/**
 * @author Kazi
 */
public class LoginRequestDto implements CommonRequestDto {

    private final String username;
    private final String password;

    /**
     * @param username
     * @param password
     */
    public LoginRequestDto(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginRequestDto [username=" + username + ", password=" + password + "]";
    }

}
