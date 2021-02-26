package userapi.util;

import java.util.Date;

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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import userapi.models.UserPrincipal;

@Component
public class JwtTokenProvider {
    
    private final Environment env;

    /*
        This class is used for generating JWT token and handling the actions with JWT token
    */

    @Autowired
    public JwtTokenProvider(Environment env) {
        this.env = env;
    }

    public String generateJwtToken(UserPrincipal userPrincipal) {
        final long expirationTime = Long.parseLong(env.getProperty("jwt.token.expirationTime"));
        
        return JWT.create()
            .withIssuedAt(new Date())
            .withSubject(userPrincipal.getUsername())
            .withClaim("id", userPrincipal.getUser().getId())
            .withClaim("email", userPrincipal.getUser().getEmail())
            .withClaim("application", "app-learning")
            .withExpiresAt(new Date( System.currentTimeMillis() + expirationTime ))
            .sign(Algorithm.HMAC512(env.getProperty("jwt.token.secret")));
    }

    public Authentication getAuthentication(String username, HttpServletRequest req) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String username, String token) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(token)) {
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

}
