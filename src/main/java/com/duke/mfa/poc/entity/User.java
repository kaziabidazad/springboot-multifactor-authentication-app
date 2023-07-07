/**
 * 
 */
package com.duke.mfa.poc.entity;

import java.io.Serializable;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Kazi
 *
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 3381353402627107064L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "username")
    private String username;

    @Column(name = "pass_hash")
    private byte[] hash;

    @Column(name = "pass_salt")
    private byte[] salt;

    @Column(name = "totp_secret")
    private char[] totpSecret;

    @Column(name = "mfa_registered")
    private boolean mfaRegistered;

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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public char[] getTotpSecret() {
        return totpSecret;
    }

    public void setTotpSecret(char[] totpSecret) {
        this.totpSecret = totpSecret;
    }

    public boolean isMfaRegistered() {
        return mfaRegistered;
    }

    public void setMfaRegistered(boolean mfaRegistered) {
        this.mfaRegistered = mfaRegistered;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", emailId=" + emailId + ", username=" + username
                + ", hash=" + Arrays.toString(hash) + ", salt=" + Arrays.toString(salt) + ", totpSecret="
                + Arrays.toString(totpSecret) + ", mfaRegistered=" + mfaRegistered + "]";
    }

}
