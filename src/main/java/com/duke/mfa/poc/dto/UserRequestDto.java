package com.duke.mfa.poc.dto;

/**
 * @author Kazi
 */
public class UserRequestDto implements CommonRequestDto {
    private Integer useId;
    private String name;
    private String username;
    private String email;
    private String password;

    public UserRequestDto() {
    }

    /**
     * @param name
     * @param username
     * @param email
     * @param password
     */
    public UserRequestDto(String name, String username, String email, String password) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * @param useId
     * @param name
     * @param username
     * @param email
     * @param password
     */
    public UserRequestDto(Integer useId, String name, String username, String email, String password) {
        super();
        this.useId = useId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
        this.useId = useId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto [name=" + name + ", username=" + username + ", email=" + email + ", password=" + password + "]";
    }

}
