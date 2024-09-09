package com.exalt.training.users.service;

import com.exalt.training.users.dto.UserDTO;
import com.exalt.training.users.exception.EmailAlreadyUsedException;
import com.exalt.training.users.exception.InvalidCredentialsException;
import com.exalt.training.users.exception.UserBlockedException;
import com.exalt.training.users.exception.UserNotFoundException;
import com.exalt.training.users.model.User;
import com.exalt.training.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for handling user authentication operations such as sign up, sign in, blocking, and unblocking users.
 */
@Data
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    /**
     * Signs up a new user by creating a User entity and saving it to the repository.
     *
     * @param userDTO the user data transfer object containing user details.
     * @return the saved User entity.
     * @throws EmailAlreadyUsedException if the email provided is already in use.
     */
    public User signUp(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("Email is already used");
        }
        User user = new User();
        user.setNationalId(userDTO.getNationalId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRule(userDTO.getRule());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBalance(userDTO.getBalance());
        return userRepository.save(user);
    }

    /**
     * Signs in a user by verifying their email and password.
     *
     * @param email    the email of the user.
     * @param password the password of the user.
     * @return the User entity if credentials are valid.
     * @throws UserNotFoundException       if the user is not found.
     * @throws UserBlockedException       if the user is blocked.
     * @throws InvalidCredentialsException if the provided credentials are invalid.
     * @throws IllegalStateException       if the API call to check if the user is blocked fails or returns an invalid response.
     */
    public User signIn(String email, String password) {
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://BLOCKED-USERS/exalt/training/userscontrol/isBlocked/{nationalId}", Map.class,foundUser.getNationalId());
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Failed to determine if user is blocked: " + responseEntity.getStatusCode());
        }
        Map<String, Object> responseBody = responseEntity.getBody();
        boolean isBlocked = false;
        if (responseBody != null && responseBody.containsKey("isBlocked")) {
            isBlocked = (Boolean) responseBody.get("isBlocked");
        } else {
            throw new IllegalStateException("Invalid response from isBlocked API");
        }
        if (isBlocked) {
            throw new UserBlockedException("User is blocked");
        }
        if (!passwordEncoder.matches(password, foundUser.getPassword())) {
            foundUser.setFailedAttempts(foundUser.getFailedAttempts() + 1);
            userRepository.save(foundUser);

            if (foundUser.getFailedAttempts() >= 3) {
                blockUser(foundUser.getNationalId());
            }
            throw new InvalidCredentialsException("Invalid credentials");
        }
        foundUser.setLoggedIn(true);
        foundUser.setFailedAttempts(0);
        userRepository.save(foundUser);
        return foundUser;
    }

    /**
     * Blocks a user by sending a request to an external service and updating the user status.
     *
     * @param nationalId the national ID of the user to be blocked.
     * @throws UserNotFoundException if the user is not found.
     * @throws IllegalStateException if the API call to block the user fails.
     */
    public void blockUser(String nationalId) {
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Map<String, Object> userData = new HashMap<>();
        userData.put("nationalId", nationalId);
        userData.put("userName", user.getUsername());

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://BLOCKED-USERS/exalt/training/userscontrol/block", userData, Map.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Failed to block user: " + responseEntity.getStatusCode());
        }
    }

    /**
     * Unblocks a user by sending a request to an external service and updating the user status.
     *
     * @param nationalId the national ID of the user to be unblocked.
     * @throws UserNotFoundException if the user is not found.
     * @throws IllegalStateException if the API call to unblock the user fails.
     */

    public void unblockUser(String nationalId) {
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String unblockUrl = "http://BLOCKED-USERS/exalt/training/userscontrol/unblock/{nationalId}";
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(unblockUrl, null, Map.class,user.getNationalId());
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Failed to unblock user: " + responseEntity.getStatusCode());
        }
        user.setFailedAttempts(0);
        userRepository.save(user);
    }
}
