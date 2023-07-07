package com.duke.mfa.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.duke.mfa.poc.component.CustomAuthProvider;
import com.duke.mfa.poc.component.Principal;
import com.duke.mfa.poc.dto.LoginRequestDto;
import com.duke.mfa.poc.dto.LoginResponseDto;
import com.duke.mfa.poc.utils.Endpoints;

/**
 * @author Kazi
 */
@RestController
public class LoginController implements Endpoints {

    @Autowired
    private CustomAuthProvider customAuthProvider;

    @PostMapping(path = LOGIN)
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        Principal principal = customAuthProvider.authenticate(loginRequest);
        LoginResponseDto response = new LoginResponseDto(
                "Login Successful, please complete the 2nd factor authentication", principal.getUserId(),
                principal.getName(), principal.getUsername(), principal.getAuthTokenString(),
                principal.isMfAuthenticated());
        return response;
    }

}
