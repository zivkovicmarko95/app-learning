package com.example.userapi.businessservices;

import java.util.List;

import com.example.userapi.exceptions.EmailExistException;
import com.example.userapi.exceptions.EmailNotFoundException;
import com.example.userapi.exceptions.NotValidIdException;
import com.example.userapi.exceptions.UserNotFoundException;
import com.example.userapi.exceptions.UsernameExistException;
import com.example.userapi.models.User;
import com.example.userapi.services.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessService {
    
    @Autowired
    private UserServiceImpl userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public User findById(String id) throws NotValidIdException {
        return userService.findById(id);
    }

    public List<User> findAll() {
        return userService.findAll();
    }

    public User save(User object) {
        return userService.save(object);
    }

    public void deleteById(String id) throws NotValidIdException {
        logger.info("Deleting user with id {}", id);
        
        userService.deleteById(id);

        logger.info("User deleted");
    }

    public void deleteAll() {
        userService.deleteAll();
    }

    public User register(String firstName, String lastName, String username, String email, String password) throws UserNotFoundException, UsernameExistException, EmailExistException {
        return userService.register(firstName, lastName, username, email, password);
    }

    public User findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userService.findUserByEmail(email);
    }

    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
            String newEmail, boolean isNotLocked, boolean isActive) throws UserNotFoundException, UsernameExistException, EmailExistException {
        return userService.updateUser(currentUsername, newFirstName, newLastName, 
                newUsername, newEmail, isNotLocked, isActive);
    }

    public User resetPassword(String email, String newPassword) throws EmailNotFoundException {
        return userService.resetPassword(email, newPassword);
    }

}
