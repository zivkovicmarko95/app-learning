package userapi.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import userapi.models.UserPrincipal;
import userapi.services.LoginAttemptService;

/**
 * Whenever user tries to login to the application, 2 events will be triggered
 * 1st - user will be logged in succesfuly
 * 2nd - user will not be logged in succesfuly
 */

@Component
public class AuthenticationSuccessListerner {
    
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationSuccessListerner(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            UserPrincipal user = (UserPrincipal) principal;
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

}
