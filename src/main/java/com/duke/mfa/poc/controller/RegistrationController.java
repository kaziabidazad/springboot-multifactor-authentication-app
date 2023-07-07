package com.duke.mfa.poc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.duke.mfa.poc.dto.UserRegistrationResponseDto;
import com.duke.mfa.poc.dto.UserRequestDto;
import com.duke.mfa.poc.service.UserService;
import com.duke.mfa.poc.utils.Endpoints;

/**
 * @author Kazi
 */
@RestController
public class RegistrationController implements Endpoints {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @PostMapping(path = REGISTER_USER)
    public UserRegistrationResponseDto registerUser(@RequestBody UserRequestDto userDto) {
        validate(userDto);
        UserRegistrationResponseDto response = null;
        try {
            response = userService.registerUser(userDto);
        } catch (Exception e) {
            LOGGER.error("Error Registering User", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return response;

    }

    private void validate(UserRequestDto userRequest) {
        if (userRequest == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is empty.");
        if (userRequest.getEmail() == null || userRequest.getEmail().isBlank() || userRequest.getName() == null
                || userRequest.getName().isBlank() || userRequest.getPassword() == null
                || userRequest.getPassword().isBlank() || userRequest.getUsername() == null
                || userRequest.getUsername().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required fields are null.");
    }

}
