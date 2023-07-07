package com.duke.mfa.poc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.duke.mfa.poc.component.TokenAuthorizationFilter;
import com.duke.mfa.poc.component.UserTokenManager;
import com.duke.mfa.poc.utils.Endpoints;

/**
 * @author Kazi
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig implements Endpoints {

    @Autowired
    private UserTokenManager tokenManager;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return null;
    }

    /**
     * Creating a Security Filter chain bean to use a custom filter for api starting
     * with /api/** <br>
     * We can have multiple {@link SecurityFilterChain} beans for multiple such
     * entry points and we can define individual filters for them.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain filterChain1(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable()).securityMatcher(API + "/**")
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.anyRequest().authenticated();
                })
                .addFilterBefore(new TokenAuthorizationFilter(tokenManager), UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLogin -> {
                    formLogin.disable();
                }).logout((logout) -> {
                    logout.logoutUrl("/logout").permitAll();
                }).exceptionHandling(exceptionHandling -> exceptionHandling.disable());
        return httpSecurity.build();
    }

    /**
     * 1. Disabling the authentication on few of the open endpoints. 2. Disabling
     * form login and making the application stateless.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain2(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable())
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers(BASE, HEALTH, HOME, LOGIN, REGISTER_USER).permitAll();
                }).formLogin(formLogin -> {
                    formLogin.disable();
                }).logout((logout) -> {
                    logout.logoutUrl("/logout").permitAll();
                });
        return httpSecurity.build();
    }

}
