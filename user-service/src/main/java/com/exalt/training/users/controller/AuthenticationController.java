package com.exalt.training.users.controller;

import com.exalt.training.users.dto.UserDTO;
import com.exalt.training.users.exception.EmailAlreadyUsedException;
import com.exalt.training.users.exception.InvalidCredentialsException;
import com.exalt.training.users.exception.UserBlockedException;
import com.exalt.training.users.exception.UserNotFoundException;
import com.exalt.training.users.model.User;
import com.exalt.training.users.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling authentication-related requests.
 * This class provides endpoints for user sign-up, sign-in, blocking, and unblocking.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/exalt/training/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService; //Service for user authentication

    /**
     * Endpoint for user sign-up.
     *
     * @param userDTO the data transfer object containing user details.
     * @param bindingResult the result of validating the userDTO.
     * @return a ResponseEntity containing the result of the sign-up operation.
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Validation failed");
            response.put("errors", bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toArray());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            User createdUser = authenticationService.signUp(userDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.CREATED.value());
            response.put("message", "User registered successfully");
            response.put("user", createdUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EmailAlreadyUsedException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for user sign-in.
     *
     * @param credentials a map containing the user's email and password.
     * @return a ResponseEntity containing the result of the sign-in operation.
     */
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, String> credentials) {
        try {
            User user = authenticationService.signIn(credentials.get("email"), credentials.get("password"));
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "User signed in successfully");
            response.put("user", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | InvalidCredentialsException | UserBlockedException | IllegalStateException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for blocking a user by their national ID.
     *
     * @param nationalId the national ID of the user to be blocked.
     * @return a ResponseEntity containing the result of the block operation.
     */
    @PostMapping("/block/{nationalId}")
    public ResponseEntity<Map<String, Object>> blockUser(@PathVariable("nationalId") String nationalId) {
        try {
            authenticationService.blockUser(nationalId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "User blocked successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | IllegalStateException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for unblocking a user by their national ID.
     *
     * @param nationalId the national ID of the user to be unblocked.
     * @return a ResponseEntity containing the result of the unblock operation.
     */
    @PostMapping("/unblock/{nationalId}")
    public ResponseEntity<Map<String, Object>> unblockUser(@PathVariable("nationalId") String nationalId) {
        try {
            authenticationService.unblockUser(nationalId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "User unblocked successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException | IllegalStateException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
