package com.example.productapi.filter;

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

import com.example.productapi.util.JwtHelper;

@Component
public class JwtHeaderFilter extends OncePerRequestFilter {
    
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

        if (token == null || !token.startsWith(env.getProperty("jwt.token.header"))) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtHelper.isJwtTokenValid(token)) {
                final String subject = jwtHelper.getSubject(token);
                final List<GrantedAuthority> authorities = jwtHelper.getAuthorities(token);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

       filterChain.doFilter(request, response);
    }

}
