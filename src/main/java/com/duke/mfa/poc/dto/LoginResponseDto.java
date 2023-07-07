package com.duke.mfa.poc.dto;

/**
 * @author Kazi
 */
public final class LoginResponseDto extends ResponseDto {

    private final Integer userId;
    private final String username;
    private final String name;
    private final String token;
    private final boolean mfaAuthenticated;

    
    
    /**
     * @param message
     * @param userId
     * @param username
     * @param name
     * @param token
     * @param mfaAuthenticated
     */
    public LoginResponseDto(String message, Integer userId, String username, String name, String token,
            boolean mfaAuthenticated) {
        super(message);
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.token = token;
        this.mfaAuthenticated = mfaAuthenticated;
    }

    /**
     * @param userId
     * @param username
     * @param name
     * @param token
     * @param mfaAuthenticated
     */
    public LoginResponseDto(Integer userId, String name, String username, String token, boolean mfaAuthenticated) {
        super();
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.token = token;
        this.mfaAuthenticated = mfaAuthenticated;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public boolean isMfaAuthenticated() {
        return mfaAuthenticated;
    }

}
