package com.example.monitoringapi.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.monitoringapi.util.JwtHelper;

@Component
public class JwtHeaderFilter extends OncePerRequestFilter {
    
    /*
        JwtHeaderFilter is used for checking if the JWT token exists in the HTTP header and if the JWT token is
        valid or not. If the token is valid, request will be checked and processed and if not, user will get 401
        response
    */

    private final Environment env;
    private final JwtHelper jwtHelper;

    @Autowired
    public JwtHeaderFilter(Environment env, JwtHelper jwtHelper) {
        this.env = env;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Checking jwt token
        if (token == null || !token.startsWith(env.getProperty("jwt.token.header"))) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            
            if (jwtHelper.isJwtTokenValid(token)) {
                // final JwtSubject subject = jwtHelper.getSubject(token);
                final String username = jwtHelper.getSubject(token);
                final List<GrantedAuthority> authorities = jwtHelper.getAuthorities(token);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

       filterChain.doFilter(request, response);
    }

}
