package com.duke.mfa.poc.dto;

/**
 * @author Kazi
 */
public class UserRegistrationResponseDto extends ResponseDto implements CommonResponseDto {

    private Integer userId;
    private String name;

    public UserRegistrationResponseDto() {
    }

    /**
     * @param message
     */
    public UserRegistrationResponseDto(String message) {
        super(message);
    }

    /**
     * @param message
     * @param userId
     * @param name
     */
    public UserRegistrationResponseDto(String message, Integer userId, String name) {
        super(message);
        this.userId = userId;
        this.name = name;
    }

    /**
     * @param userId
     * @param name
     */
    public UserRegistrationResponseDto(Integer userId, String name) {
        super();
        this.userId = userId;
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserRegistrationResponseDto [userId=" + userId + ", name=" + name + "]";
    }

}
