package userapi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import userapi.constants.Authority;
import userapi.models.UserPrincipal;

@Component
public class JwtTokenProvider {
    
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    
    private final Environment env;

    /*
        This class is used for generating JWT token and handling the actions with JWT token
    */

    @Autowired
    public JwtTokenProvider(Environment env) {
        this.env = env;
    }

    public String generateJwtToken(UserPrincipal userPrincipal) {
        final String[] authorities = getAuthoritiesForUser(userPrincipal);
        final long expirationTime = Long.parseLong(env.getProperty("jwt.token.expirationTime"));
        String[] permissions = Authority.USER_AUTHORITIES;

        if (authorities[0].equals(ROLE_ADMIN)) {
            permissions = Authority.ADMIN_AUTHORITIES;
        } else if (authorities[0].equals(ROLE_SUPER_ADMIN)) {
            permissions = Authority.SUPER_ADMIN_AUTHORITIES;
        }
        
        return JWT.create()
            .withIssuedAt(new Date())
            .withSubject(userPrincipal.getUsername())
            .withClaim("id", userPrincipal.getUser().getId())
            .withClaim("email", userPrincipal.getUser().getEmail())
            .withClaim("application", "app-learning")
            .withArrayClaim(env.getProperty("jwt.token.authorities"), authorities)
            .withArrayClaim("permissions", permissions)
            .withExpiresAt(new Date( System.currentTimeMillis() + expirationTime ))
            .sign(Algorithm.HMAC512(env.getProperty("jwt.token.secret")));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        final String[] authorities = getAuthoritiesFromToken(token);

        return Arrays.stream(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest req) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String username, String token) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(token)) {
            return false;
        }

        JWTVerifier verifiter = getJwtVerifier();

        return !isTokenExpired(verifiter, token);
    }

    public String getSubject(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        
        JWTVerifier verifiter = getJwtVerifier();
        
        return verifiter.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifiter, String token) {
        Date expDate = verifiter.verify(token).getExpiresAt();

        return expDate.before(new Date());
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier jwtverVerifier = null;

        try {
            Algorithm algorithm = Algorithm.HMAC512(env.getProperty("jwt.token.secret"));
            jwtverVerifier = JWT.require(algorithm).build();
        } catch (JWTVerificationException e) {
            return null;
        }

        return jwtverVerifier;
    }

    private String[] getAuthoritiesForUser(UserPrincipal userPrincipal) {
        List<String> authorities = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }

        return authorities.toArray(new String[0]);
    }

    private String[] getAuthoritiesFromToken(String token) {
        final JWTVerifier verifier = getJwtVerifier();
        
        return verifier.verify(token).getClaim(env.getProperty("jwt.token.authorities")).asArray(String.class);
    }

}
