package userapi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import userapi.constants.MessagesConstants;
import userapi.enumeration.Role;
import userapi.exceptions.EmailExistException;
import userapi.exceptions.EmailNotFoundException;
import userapi.exceptions.NotValidIdException;
import userapi.exceptions.UserNotFoundException;
import userapi.exceptions.UsernameExistException;
import userapi.models.Monitoring;
import userapi.models.User;
import userapi.models.UserPrincipal;
import userapi.repositories.UserRepository;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    /*
     * UserService which is used for handling user actions like login, finding user
     * by id, finding all the users, saving user to the database, deleting user by
     * id, deleting all the users, registering user, finding user by username and
     * finding user by email, updating user and reseting user's password
     */

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MonitoringService monitoringService;
    private final LoginAttemptService loginAttemptService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            MonitoringService monitoringService, LoginAttemptService loginAttemptService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.monitoringService = monitoringService;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            logger.error(MessagesConstants.NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(MessagesConstants.NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);

            logger.info(MessagesConstants.RETURNING_FOUND_USER_BY_USERNAME + username);

            return userPrincipal;
        }
    }

    @Override
    public User findById(String id) throws NotValidIdException {
        if (!ObjectId.isValid(id)) {
            throw new NotValidIdException(MessagesConstants.NOT_VALID_ID + id);
        }

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User object) {
        sendMonitoringMessage(MessagesConstants.USER_CREATED + object.toString());
        return userRepository.save(object);
    }

    @Override
    public void deleteById(String id) throws NotValidIdException {
        if (!ObjectId.isValid(id)) {
            throw new NotValidIdException(MessagesConstants.NOT_VALID_ID + id);
        }

        sendMonitoringMessage(MessagesConstants.USER_DELETED + id);
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        sendMonitoringMessage(MessagesConstants.USER_COLLECTION_DELETED);
        userRepository.deleteAll();
    }

    @Override
    public User register(String firstName, String lastName, String username, String email, String password)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);

        User user = new User();

        user.setUserId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptPassword(password));
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        user.setIsActive(true);
        user.setIsNotLocked(true);

        User savedUser = save(user);

        return savedUser;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
            String newEmail, boolean isNotLocked, boolean isActive)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        User user = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);

        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setUsername(newUsername);
        user.setEmail(newEmail);
        user.setIsNotLocked(isNotLocked);
        user.setIsActive(isActive);

        userRepository.save(user);

        return user;
    }

    @Override
    public User resetPassword(String email, String newPassword) throws EmailNotFoundException {
        sendMonitoringMessage(MessagesConstants.USER_RESTART_PASSWORD + email);

        User user = findUserByEmail(email);

        if (user == null) {
            throw new EmailNotFoundException(MessagesConstants.NO_USER_FOUND_BY_EMAIL + email);
        }

        String password = encryptPassword(newPassword);
        user.setPassword(password);
        User savedUser = save(user);
        return savedUser;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String email)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(email);

        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);

            if (currentUser == null) {
                throw new UserNotFoundException(MessagesConstants.NO_USER_FOUND_BY_USERNAME);
            }

            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(MessagesConstants.USERNAME_ALREADY_EXISTS + newUsername);
            }

            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(MessagesConstants.EMAIL_ALREADY_EXISTS + email);
            }

            return currentUser;
        } else {
            if (userByNewUsername != null) {
                throw new UsernameExistException(MessagesConstants.USERNAME_ALREADY_EXISTS + newUsername);
            }

            if (userByNewEmail != null) {
                throw new EmailExistException(MessagesConstants.EMAIL_ALREADY_EXISTS + email);
            }

            return null;
        }

    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }

    private String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private void validateLoginAttempt(User user) {
        if (user.getIsNotLocked()) {
            if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setIsNotLocked(false);
                save(user);
            } 
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private void sendMonitoringMessage(String message) {
        Monitoring monitoring = new Monitoring(message, MessagesConstants.USER_API);

        logger.info(MessagesConstants.SENDING_MESSAGE_TO_RABBITMQ);

        monitoringService.sendMessageToMonitoringApi(monitoring);
    }

}
