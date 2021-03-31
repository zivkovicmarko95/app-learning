package com.example.productapi.services;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Query query = new Query(Criteria.where("username").is(username));

        Document user = mongoTemplate.findOne(query, Document.class, "user");

        if (user == null) {
            throw new UsernameNotFoundException(
                String.format("User with provided username {} does not exist!", username)
            );
        }
        
        return User.withUsername(username)
                    .password(user.getString("password"))
                    .roles(user.getString("role"))
                    .build();
    }

}
