package com.example.userapi.services;

import java.util.List;

import com.example.userapi.exceptions.EmailExistException;
import com.example.userapi.exceptions.EmailNotFoundException;
import com.example.userapi.exceptions.NotValidIdException;
import com.example.userapi.exceptions.UserNotFoundException;
import com.example.userapi.exceptions.UsernameExistException;
import com.example.userapi.models.User;

public interface UserService {

    List<User> findAll();
    User findById(String id) throws NotValidIdException;
    User save(User object);

    void deleteById(String id) throws NotValidIdException;
    void deleteAll();
    User register(String firstName, String lastName, String username, String email, String password) 
            throws UserNotFoundException, UsernameExistException, EmailExistException;
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, boolean isNotLocked, boolean isActive) 
            throws UserNotFoundException, UsernameExistException, EmailExistException;
    User resetPassword(String email, String newPassword) throws EmailNotFoundException;

}
