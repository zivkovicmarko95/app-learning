package com.example.userapi.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.example.userapi.services.LoginAttemptService;

/**
 * Whenever user tries to login to the application, 2 events will be triggered
 * 1st - user will be logged in succesfuly
 * 2nd - user will not be logged in succesfuly
 */

@Component
public class AuthenticationFailureListener {
    
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();

        if (principal instanceof String) {
            String username = (String) principal;
            loginAttemptService.addUserToLoginAttemptCache(username);
        }
    }


}
