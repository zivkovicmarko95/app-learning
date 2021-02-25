package userapi.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import userapi.util.JwtTokenProvider;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    private JwtTokenProvider jwtTokenProvider;
    private Environment env;

    @Autowired
    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, Environment env) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if (request.getMethod().equalsIgnoreCase(env.getProperty("jwt.token.optionsHTTPMethod"))) {
            response.setStatus(HttpStatus.OK.value());
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(env.getProperty("jwt.token.header"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(env.getProperty("jwt.token.header").length()).trim();
        String subject = jwtTokenProvider.getSubject(token);
        
        if (jwtTokenProvider.isTokenValid(subject, token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(subject, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}
