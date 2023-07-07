package com.duke.mfa.poc.component;

import java.util.ArrayList;
import java.util.List;

import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.duke.mfa.poc.dto.LoginRequestDto;
import com.duke.mfa.poc.dto.LoginResponseDto;
import com.duke.mfa.poc.dto.TotpAuthRequestDto;
import com.duke.mfa.poc.entity.User;
import com.duke.mfa.poc.repo.UserRepository;
import com.duke.mfa.poc.utils.PasswordUtils;

/**
 * @author Kazi
 */
@Component
public class CustomAuthProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenManager userTokenManager;

    /**
     * Authenticates against username and password and returns a {@link Principal}.
     * 
     * @param loginRequest
     * @return
     * @throws AuthenticationException
     */
    public Principal authenticate(LoginRequestDto loginRequest) throws AuthenticationException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        /*
         * Support login with username as well as email id
         */
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmailId(username);
        }
        if (user == null) {
            throw new BadCredentialsException("Invalid User name/password");
        }
        boolean passwordValid = PasswordUtils.validatePassword(password, user.getHash(), user.getSalt());
        if (passwordValid) {
            // Generate the auth token
            char[] token = PasswordUtils.generatePassword(128, true);
            /*
             * Assigning a temporary role since 2nd factor authentication is not yet done.
             * Once 2nd factor is authenticated, actual roles can be assigned.
             */
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority("ROLE_TEMP");
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(tempAuthority);
            Principal principal = new Principal(user.getUserId(), user.getName(), username, "invalid", false, token,
                    user.getTotpSecret(), authorities);
            // Updating the cache to store the token
            userTokenManager.addTokenToCache(principal);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
                    password);
            // Adding authentication to spring security
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return principal;
        }
        throw new BadCredentialsException("Invalid username/password");
    }

    public LoginResponseDto authenticateTotp(TotpAuthRequestDto totpAuthRequest, Principal principal) {
        LoginResponseDto response = null;
        String totpCode = totpAuthRequest.getMfaToken();
        boolean validTotp = PasswordUtils.validateTotp(totpCode);
        if (!validTotp)
            throw new BadCredentialsException("Invalid OTP.");
        Totp totp = new Totp(PasswordUtils.getBase32TotpSecret(principal.getTotpSecret()));
        boolean totpVerified = totp.verify(totpCode);
        if (!totpVerified)
            throw new BadCredentialsException("Invalid OTP.");
        principal.setMfAuthenticated(true);
        // TODO Add roles to the principal object now.
        // Update the token cache
        userTokenManager.addTokenToCache(principal);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
                principal.getPassword());
        // Adding authentication to spring security
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response = new LoginResponseDto("Sucess", null, principal.getUsername(), principal.getName(),
                principal.getAuthTokenString(), principal.isMfAuthenticated());
        return response;
    }

}
