/**
 * 
 */
package com.duke.mfa.poc.config;

import java.io.IOException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Kazi
 *
 */
@Configuration
public class AppConfig {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocations(new ClassPathResource("datasource.properties"));
        propertyPlaceholderConfigurer.setIgnoreResourceNotFound(false);
        propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(false);
        return propertyPlaceholderConfigurer;
    }

}
