package userapi.services;

import java.util.List;

import userapi.exceptions.EmailExistException;
import userapi.exceptions.EmailNotFoundException;
import userapi.exceptions.NotValidIdException;
import userapi.exceptions.UserNotFoundException;
import userapi.exceptions.UsernameExistException;
import userapi.models.User;

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
