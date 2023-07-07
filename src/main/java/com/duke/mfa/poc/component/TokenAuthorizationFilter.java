package com.duke.mfa.poc.component;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.duke.mfa.poc.dto.ResponseDto;
import com.duke.mfa.poc.utils.BasicUtils;
import com.duke.mfa.poc.utils.Endpoints;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Kazi
 */
public class TokenAuthorizationFilter extends OncePerRequestFilter implements Endpoints {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthorizationFilter.class);

    private UserTokenManager userTokenManager;

    /**
     * @param authProvider
     * @param userTokenManager
     */
    public TokenAuthorizationFilter(UserTokenManager userTokenManager) {
        super();
        this.userTokenManager = userTokenManager;
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        String authToken = req.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authToken == null) {
//            ResponseDto responseDto = new ResponseDto("Missing Auth Token");
//            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            res.setStatus(HttpStatus.FORBIDDEN.value());
//            res.getWriter().write(BasicUtils.unPretify(responseDto));
//            res.getWriter().flush();
//            return;
//        }
//        //
////        authenticate
//        Principal principal = userTokenManager.getUserDetails(authToken);
//        if (principal == null) {
//            ResponseDto responseDto = new ResponseDto("Access Denied");
//            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            res.setStatus(HttpStatus.FORBIDDEN.value());
//            res.getWriter().write(BasicUtils.unPretify(responseDto));
//            res.getWriter().flush();
//            return;
//        }
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal,
//                principal.getPassword(), principal.getAuthorities()));
//        LOGGER.info("Authentication done...");
//        chain.doFilter(request, response);
//    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String endpoint = request.getServletPath();
        if (authToken == null) {
            sendResponseMessage(response, "Missing Auth Token", HttpStatus.FORBIDDEN);
            return;
        }
        //
//        authenticate
        Principal principal = userTokenManager.getUserDetails(authToken);
        if (principal == null) {
            sendResponseMessage(response, "Access Denied", HttpStatus.FORBIDDEN);
            return;
        }
        // check MFA
        boolean mfaAuthenticated = principal.isMfAuthenticated();
        // Allow MFA registration related APIs to pass through
        if (!mfaAuthenticated
                && !(endpoint.equals(API + MFA + AUTHENTICATE_MFA) || endpoint.equals(API + MFA + REGISTER_TOTP)
                        || endpoint.equals(API + MFA + UPDATE_MFA) || endpoint.equals(API + MFA + GET_QR_CODE))) {
            sendResponseMessage(response, "Please complete the 2nd factor authentication.", HttpStatus.OK);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal,
                principal.getPassword(), principal.getAuthorities()));
        LOGGER.info("Authentication done...");
        filterChain.doFilter(request, response);
    }

    private void sendResponseMessage(HttpServletResponse response, String message, HttpStatus httpStatus)
            throws IOException {
        ResponseDto responseDto = new ResponseDto(message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        response.getWriter().write(BasicUtils.unPretify(responseDto));
        response.getWriter().flush();
    }

}
