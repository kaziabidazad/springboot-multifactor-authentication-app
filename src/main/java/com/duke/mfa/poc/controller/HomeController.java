/**
 * 
 */
package com.duke.mfa.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duke.mfa.poc.dto.ResponseDto;
import com.duke.mfa.poc.utils.Endpoints;

/**
 * @author Kazi
 *
 */
@RestController
public class HomeController implements Endpoints {

    @GetMapping(path = { BASE, HOME, HEALTH })
    public ResponseDto healthCheck() {
        return new ResponseDto("Welcome to MFA App!");
    }
}
