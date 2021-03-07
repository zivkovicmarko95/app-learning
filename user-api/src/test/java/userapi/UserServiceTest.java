package userapi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import userapi.enumeration.Role;
import userapi.exceptions.EmailExistException;
import userapi.exceptions.EmailNotFoundException;
import userapi.exceptions.NotValidIdException;
import userapi.exceptions.UserNotFoundException;
import userapi.exceptions.UsernameExistException;
import userapi.models.User;
import userapi.repositories.UserRepository;
import userapi.services.LoginAttemptService;
import userapi.services.MonitoringServiceImpl;
import userapi.services.UserServiceImpl;

@DataMongoTest
public class UserServiceTest {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private RabbitTemplate rabbitTemplate;

    private LoginAttemptService loginAttemptService;

    private MonitoringServiceImpl monitoringService;

    private UserServiceImpl userService;

    private final String USER_FIRSTNAME = "Test";
    private final String USER_LASTNAME = "Testing";
    private final String USER_USERNAME = "test.testing";
    private final String USER_EMAIL = "test.testing@email.com";
    private final String USER_PASSWORD = "testing1234";

    @BeforeEach
    public void setup() {
        rabbitTemplate = Mockito.mock(RabbitTemplate.class);

        monitoringService = new MonitoringServiceImpl(rabbitTemplate, env);
        loginAttemptService = new LoginAttemptService();

        userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, monitoringService,
                loginAttemptService);

        if (userRepository.findUserByUsername(USER_USERNAME) != null) {
            User user = userRepository.findUserByUsername(USER_USERNAME);
            userRepository.deleteById(user.getId());
        } else if (userRepository.findUserByEmail(USER_EMAIL) != null) {
            User user = userRepository.findUserByEmail(USER_EMAIL);
            userRepository.deleteById(user.getId());
        }

        User user = new User(UUID.randomUUID().toString(), USER_USERNAME, USER_LASTNAME, USER_USERNAME,
                bCryptPasswordEncoder.encode(USER_PASSWORD), USER_EMAIL, Role.ROLE_USER.name(),
                Role.ROLE_USER.getAuthorities(), true, true);
        userRepository.save(user);
    }

    @Test
    public void testRegisterSuccesfully()
            throws UserNotFoundException, UsernameExistException, EmailExistException, NotValidIdException {
        User deleteUser = userService.findUserByUsername(USER_USERNAME);
        if (deleteUser != null) {
            userService.deleteById(deleteUser.getId());
        }
        User user = userService.register(USER_FIRSTNAME, USER_LASTNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD);

        assertNotNull(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testRegisterUnsecesfully() throws UserNotFoundException, UsernameExistException, EmailExistException {
        assertThrows(UsernameExistException.class,
                () -> userService.register(USER_FIRSTNAME, USER_LASTNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD));
    }

    @Test
    public void testUserByUsernameSucesfully() {
        UserDetails userDetails = userService.loadUserByUsername(USER_USERNAME);

        assertNotNull(userDetails);
    }

    @Test
    public void testLoadUserByUsernameUnsecesfully() {
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(UUID.randomUUID().toString()));
    }

    @Test
    public void testDeleteUserById() throws NotValidIdException {
        User user = userService.findUserByUsername(USER_USERNAME);

        userService.deleteById(user.getId());

        assertNull(userService.findUserByUsername(USER_USERNAME));
        assertThrows(NotValidIdException.class, () -> userService.deleteById(UUID.randomUUID().toString()));
    }

    @Test
    public void testResetPasswordSuccesfully() throws EmailNotFoundException {
        User user = userService.findUserByEmail(USER_EMAIL);
        User resetPassword = userService.resetPassword(USER_EMAIL, UUID.randomUUID().toString());

        assertNotNull(resetPassword);
        assertNotEquals(user, resetPassword);
    }

    @Test
    public void testResetPasswordUnseccesfully() throws NotValidIdException {
        User user = userService.findUserByEmail(USER_EMAIL);
        userService.deleteById(user.getId());
        assertThrows(EmailNotFoundException.class,
                () -> userService.resetPassword(USER_EMAIL, UUID.randomUUID().toString()));
    }

    @Test
    public void testUpdateUserSuccesfully() throws UserNotFoundException, UsernameExistException, EmailExistException {
        User user = userService.findUserByEmail(USER_EMAIL);
        User updatedUser = userService.updateUser(USER_USERNAME, "newFirstName", "newLastName", "newUserUsername",
                "newUserEmail@email.com", true, true);

        assertNotNull(updatedUser);
        assertNotEquals(user, updatedUser);

        userRepository.deleteById(updatedUser.getId());;
    }

    @Test
    public void testUpdatedUserUnsuccesfully() {
        String username = "qwerty";
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(username, USER_FIRSTNAME, USER_LASTNAME, USER_USERNAME, USER_EMAIL, true, true));
    }

}
