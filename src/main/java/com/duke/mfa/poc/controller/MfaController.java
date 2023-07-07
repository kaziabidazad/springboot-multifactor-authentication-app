package com.duke.mfa.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duke.mfa.poc.component.CustomAuthProvider;
import com.duke.mfa.poc.component.Principal;
import com.duke.mfa.poc.dto.LoginResponseDto;
import com.duke.mfa.poc.dto.TotpAuthRequestDto;
import com.duke.mfa.poc.dto.TotpRegistrationDto;
import com.duke.mfa.poc.dto.TotpRegistrationResponseDto;
import com.duke.mfa.poc.dto.UserRegistrationResponseDto;
import com.duke.mfa.poc.exception.NotImplementedException;
import com.duke.mfa.poc.service.UserService;
import com.duke.mfa.poc.utils.Endpoints;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Kazi
 */
@RestController
@RequestMapping(path = Endpoints.API + Endpoints.MFA)
public class MfaController implements Endpoints {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAuthProvider customAuthProvider;

    @PostMapping(value = REGISTER_TOTP)
    public TotpRegistrationResponseDto markTotpRegistrationDone(@RequestBody TotpRegistrationDto registration,
            Authentication authentication) {
        return userService.markTotpRegistered(((Principal) authentication.getPrincipal()).getUserId());
    }

    @GetMapping(value = GET_QR_CODE)
    public void getTotpQRCode(HttpServletResponse response, Authentication authentication) {
        userService.createAndDownloadQR(response, ((Principal) authentication.getPrincipal()).getUserId());
    }

    @PostMapping(path = UPDATE_MFA)
    public UserRegistrationResponseDto addMFA() {
        throw new NotImplementedException("Updating MFA is not yet available.. Wait for future releses.");
    }

    @PostMapping(path = AUTHENTICATE_MFA)
    public LoginResponseDto authenticateMfa(@RequestBody TotpAuthRequestDto totpRequest,
            Authentication authentication) {
        return customAuthProvider.authenticateTotp(totpRequest, ((Principal) authentication.getPrincipal()));

    }
}
