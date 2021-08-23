package com.example.demo.configuration;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //
    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();
            
        makeAuthorizationRequestHeader();
    }

    private static void makeAuthorizationRequestHeader() {
        String oauthClientId = "client";
        String oauthClientSecret = "secret";

        Encoder encoder = Base64.getEncoder();
        try {
            String toEncodeString = String.format("%s:%s", oauthClientId, oauthClientSecret);
            String authorizationRequestHeader = "Basic " + encoder.encodeToString(toEncodeString.getBytes("UTF-8"));
            log.debug("AuthorizationRequestHeader : [{}] ", authorizationRequestHeader);            // Y2xpZW50OnNlY3JldA==
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}