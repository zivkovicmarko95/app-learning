package com.example.userapi.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.springframework.stereotype.Service;

/**
 * This service is used for Brute Force Attack protection
 */

@Service
public class LoginAttemptService {

    /**
     * Constants used for Brute Force Attack protection
     * MAXIMUM_NUMBER_OF_ATTEMPTS - maximum number of login failures for specific account
     * ATTEMPT_INCREMET - if user login fails, number of failing will be increment by this value
     * CACHE_EXPIRATION_IN_MINUTES - number of minutes where username will be saved in the cache
     * MAXIMUM_SIZE_OF_CACHED_USERS - number of users saved in the cache at the same time
     */
    public static final Integer MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    public static final Integer ATTEMPT_INCREMENT = 1;
    public static final Integer CACHE_EXPIRATION_IN_MINUTES = 15;
    public static final Integer MAXIMUM_SIZE_OF_CACHED_USERS = 100;

    private LoadingCache<String, Integer> loginAttemptCache;

    public LoginAttemptService() {
        loginAttemptCache = CacheBuilder.newBuilder()
                .expireAfterWrite(CACHE_EXPIRATION_IN_MINUTES, TimeUnit.MINUTES)
                .maximumSize(MAXIMUM_NUMBER_OF_ATTEMPTS)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    /**
     * Find the user in the cache and delete the user
     * This method will be called after succesful login
     */
    public void evictUserFromLoginAttemptCache(String username) {
        loginAttemptCache.invalidate(username);
    }

    /**
     * Find the user and put it in the cache
     * This method will be called after login wasn't succesful
    */
    public void addUserToLoginAttemptCache(String username) {
        int attempts = 0;

        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        loginAttemptCache.put(username, attempts);
    }

    /**
     * Checking number of user login failures
     */
    public boolean hasExceededMaxAttempts(String username) {
        try {
            return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

}
