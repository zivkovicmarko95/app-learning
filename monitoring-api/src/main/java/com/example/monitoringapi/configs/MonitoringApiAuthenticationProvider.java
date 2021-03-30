package com.example.monitoringapi.configs;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MonitoringApiAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Criteria criteria = new Criteria();
        criteria.where("username").is(name);

        Query query = new Query(criteria);

        Document user = mongoTemplate.findOne(query, Document.class, "user");

        if (user == null) {
            throw new RuntimeException(
                String.format("User with provided username {} does not exist", name)
            );
        }

        return new UsernamePasswordAuthenticationToken(name, password, authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    
}
