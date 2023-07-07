package com.duke.mfa.poc.component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Kazi
 */
public class Principal extends User {

    private static final long serialVersionUID = 7415362135304290430L;
    private final Integer userId;
    private boolean mfAuthenticated;
    private final char[] authToken;
    private final String name;
    private final char[] totpSecret;

    /**
     * 
     * @param userId
     * @param name
     * @param username
     * @param password
     * @param mfAuthenticated
     * @param authToken
     * @param totpSecret
     * @param authorities
     */
    public Principal(Integer userId, String name, String username, String password, boolean mfAuthenticated,
            char[] authToken, char[] totpSecret, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.mfAuthenticated = mfAuthenticated;
        this.authToken = authToken;
        this.name = name;
        this.totpSecret = totpSecret;
    }

    /**
     * 
     * @param username
     * @param name
     * @param password
     * @param userId
     * @param mfAuthenticated
     * @param authToken
     * @param totpSecret
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    public Principal(String username, String name, String password, Integer userId, boolean mfAuthenticated,
            char[] authToken, char[] totpSecret, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.mfAuthenticated = mfAuthenticated;
        this.authToken = authToken;
        this.name = name;
        this.totpSecret = totpSecret;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public char[] getAuthToken() {
        return authToken;
    }

    public String getAuthTokenString() {
        return new String(authToken);
    }

    public boolean isMfAuthenticated() {
        return mfAuthenticated;
    }

    public void setMfAuthenticated(boolean mfAuthenticated) {
        this.mfAuthenticated = mfAuthenticated;
    }

    public char[] getTotpSecret() {
        return totpSecret;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(authToken);
        result = prime * result + Objects.hash(userId);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Principal other = (Principal) obj;
        return Arrays.equals(authToken, other.authToken) && Objects.equals(userId, other.userId);
    }

}
