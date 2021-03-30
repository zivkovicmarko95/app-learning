package com.example.userapi.configs;

import com.example.userapi.models.User;
import com.example.userapi.services.UserService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UserApiAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public UserApiAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findUserByUsername(name);

        if (user == null) {
            throw new RuntimeException(
                String.format("User with provided username {} is not found!", name)
            );
        }
        
        return new UsernamePasswordAuthenticationToken(name, password, authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }



}
