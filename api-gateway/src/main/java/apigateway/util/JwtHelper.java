package apigateway.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {
    
    /* 
        Class which is used for working with JWT token which has functions for 
        getting the subject from the JWT token, checking if token is valid and
        method for parsing JWT token
    */

    private final Environment env;

    @Autowired
    public JwtHelper(Environment env) {
        this.env = env;
    }

    /*
        Method getSubject returns the subject from the JWT token
    */
    public String getSubject(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        final String tokenValue = parseToken(token);
        final JWTVerifier verifier = getJwtVerifier();
        final String subject = verifier.verify(tokenValue).getSubject();

        return subject;
    }

    /* 
        Method isJwtTokenValid checks if the JWT token is valid or not. It checks if the subject
        is null or not and checks if the JWT token is expired or not
    */
    public boolean isJwtTokenValid(String token) {
        final String tokenValue = parseToken(token);
        final JWTVerifier verifier = getJwtVerifier();
        final String subject = verifier.verify(tokenValue).getSubject();
        
        if (subject != null && !isTokenExpired(tokenValue)) {
            return true;
        }

        return false;
    }

    /*
        Method parseToken uses JWT token which is in next form: Bearer tokenValue
        This method uses only tokenValue, without Bearer
    */
    public String parseToken(String token) {
        final String tokenValue = token.substring(env.getProperty("jwt.token.header").length()).trim();

        return tokenValue;
    }

    /* 
        Method getJwtVerifier returns JWT Verifier, which requirs that token is built by the HMAC512 algorithm
    */
    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier = null;
        
        try {
            Algorithm algorithm = Algorithm.HMAC512(env.getProperty("jwt.token.secret"));
            verifier = JWT.require(algorithm).build();
        } catch (Exception e) {
            return null;
        }

        return verifier;
    }

    /*
        Method isTokenExpired checks if the Token is expired or not. It checks if the expires date and time
        is before current date and time
    */
    private boolean isTokenExpired(String tokenValue) {
        final JWTVerifier verifier = getJwtVerifier();
        final Date tokeExpirationDate = verifier.verify(tokenValue).getExpiresAt();
        
        return tokeExpirationDate.before(new Date());
    }

}
