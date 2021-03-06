package com.example.userapi.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userapi.businessservices.UserBusinessService;
import com.example.userapi.constants.MessagesConstants;
import com.example.userapi.dtos.UpdateUserDTO;
import com.example.userapi.dtos.UserDTO;
import com.example.userapi.exceptions.EmailExistException;
import com.example.userapi.exceptions.EmailNotFoundException;
import com.example.userapi.exceptions.NotValidIdException;
import com.example.userapi.exceptions.UserNotFoundException;
import com.example.userapi.exceptions.UsernameExistException;
import com.example.userapi.models.HttpResponse;
import com.example.userapi.models.User;
import com.example.userapi.models.UserPrincipal;
import com.example.userapi.util.JwtTokenProvider;
import com.example.userapi.util.Mapper;

@RestController
@RequestMapping(path = { "/", UserController.BASE_URL })
public class UserController {

    /*
     * User controller is used for processing users HTTP requests and to work with
     * users profile, provide valid JWT token to the user and so on In this
     * controller, user can hit endopint for creating account, login, updating their
     * profile, finding all the profiles which exists in the database, reset
     * password, find the profile by specific name, delete profile by id and delete
     * all the users profile collection
     */

    public static final String BASE_URL = "/api/users";

    private final UserBusinessService userBusinessService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final Mapper mapper;
    private final Environment env;

    @Autowired
    public UserController(UserBusinessService userBusinessService, AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider, Mapper mapper, Environment env) {
        this.userBusinessService = userBusinessService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
        this.env = env;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        User newUser = userBusinessService.register(user.getFirstName(), user.getLastName(), user.getUsername(),
                user.getEmail(), user.getPassword());

        return new ResponseEntity<>(mapper.convertUserToUserDTO(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());

        User loginUser = userBusinessService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);

        return new ResponseEntity<>(mapper.convertUserToUserDTO(loginUser), jwtHeader, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody UpdateUserDTO userDTO)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        Boolean active = Boolean.parseBoolean(userDTO.getIsActive());
        Boolean notLocked = Boolean.parseBoolean(userDTO.getIsNotLocked());
        User updateUser = userBusinessService.updateUser(userDTO.getCurrentUsername(), userDTO.getFirstName(),
                userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(), notLocked, active);

        return new ResponseEntity<>(mapper.convertUserToUserDTO(updateUser), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userBusinessService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id) throws NotValidIdException {
        return new ResponseEntity<>(userBusinessService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody HashMap<String, String> params)
            throws EmailNotFoundException {
        userBusinessService.resetPassword(params.get("email"), params.get("newPassword"));
        return response(HttpStatus.OK, MessagesConstants.EMAIL_SENT + params.get("email"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) throws NotValidIdException {

        userBusinessService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        userBusinessService.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(env.getProperty("jwt.token.jwtTokenHeader"), jwtTokenProvider.generateJwtToken(userPrincipal));

        return headers;
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(),
                message);
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

}
