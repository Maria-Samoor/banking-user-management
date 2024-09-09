package com.exalt.training.users.controller;

import com.exalt.training.users.service.UserServices;
import com.exalt.training.users.exception.UserNotFoundException;
import com.exalt.training.users.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling user-related requests.
 * This class provides endpoints for checking balance, requesting credit and debit, and logging out.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/exalt/training/user")
public class UserController {

    private UserServices userService;

    /**
     * Endpoint for checking the balance of a user by their national ID.
     *
     * @param nationalId the national ID of the user whose balance is to be checked.
     * @return a ResponseEntity containing the user's balance.
     */
    @GetMapping("/checkBalance/{nationalId}")
    public ResponseEntity<Map<String, Object>> checkBalance(@PathVariable String nationalId) {
        try {
            User user = userService.checkBalance(nationalId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("balance", user.getBalance());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for requesting credit to a user's account.
     *
     * @param nationalId the national ID of the user requesting credit.
     * @param requestBody a map containing the amount to be credited.
     * @return a ResponseEntity with the status of the credit request.
     */
    @PostMapping("/requestCredit/{nationalId}")
    public ResponseEntity<Map<String, Object>> requestCredit(
            @PathVariable String nationalId,
            @RequestBody Map<String, Object> requestBody) {
        if (requestBody.isEmpty()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }
        Double amount = (Double) requestBody.get("amount");
        if (amount == null || amount <= 0) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid amount");
        }
        try {
            User user = userService.requestCredit(nationalId, amount);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "Credit added successfully");
            response.put("newBalance", user.getBalance());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for requesting debit from a user's account.
     *
     * @param nationalId the national ID of the user requesting debit.
     * @param requestBody a map containing the amount to be debited.
     * @return a ResponseEntity with the status of the debit request.
     */
    @PostMapping("/requestDebit/{nationalId}")
    public ResponseEntity<Map<String, Object>> requestDebit(
            @PathVariable String nationalId,
            @RequestBody Map<String, Object> requestBody) {
        if (requestBody.isEmpty()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }
        Double amount = (Double) requestBody.get("amount");
        if (amount == null || amount <= 0) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid amount");
        }
        try {
            User user = userService.requestDebit(nationalId, amount);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "Debit processed successfully");
            response.put("newBalance", user.getBalance());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for logging out a user by their national ID.
     *
     * @param nationalId the national ID of the user logging out.
     * @return a ResponseEntity with the status of the logout operation.
     */
    @PostMapping("/signout/{nationalId}")
    public ResponseEntity<Map<String, Object>> signOut(@PathVariable String nationalId) {
        try {
            userService.logoutUser(nationalId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "User logged out successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalStateException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Builds an error response with the specified status and message.
     *
     * @param status the HTTP status for the response.
     * @param message the error message to include in the response.
     * @return a ResponseEntity containing the error details.
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("statusCode", status.value());
        errorResponse.put("message", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
