package com.exalt.training.blockedusers.controller;

import com.exalt.training.blockedusers.exception.UserBlockedException;
import com.exalt.training.blockedusers.service.BlockedUsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class to handle blocking and unblocking of users.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/exalt/training/userscontrol")

public class BlockedUsersController {

    private final BlockedUsersService blockedUsersService;// Service to handle blocked users operations

    /**
     * Endpoint to block a user by national ID and username.
     *
     * @param userMap Map containing the user's national ID and username.
     * @return ResponseEntity containing the result of the operation.
     */
    @PostMapping("/block")
    public ResponseEntity<Map<String, Object>> blockUser(@RequestBody Map<String, String> userMap) {
        try {
            String nationalId = userMap.get("nationalId");
            String userName = userMap.get("userName");
            blockedUsersService.blockUser(nationalId, userName);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "User blocked successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException | UserBlockedException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to unblock a user by national ID.
     *
     * @param nationalId The national ID of the user to unblock.
     * @return ResponseEntity containing the result of the operation.
     */
    @PostMapping("/unblock/{nationalId}")
    public ResponseEntity<Map<String, Object>> unblockUser(@PathVariable("nationalId") String nationalId) {
        try {
            blockedUsersService.unblockUser(nationalId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "User unblocked successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to check if a user is blocked by national ID.
     *
     * @param nationalId The national ID of the user to check.
     * @return ResponseEntity containing whether the user is blocked.
     */
    @GetMapping("/isBlocked/{nationalId}")
    public ResponseEntity<Map<String, Object>> isUserBlocked(@PathVariable("nationalId") String nationalId) {
        boolean isBlocked = blockedUsersService.isUserBlocked(nationalId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", isBlocked ? "User is blocked" : "User is not blocked");
        response.put("isBlocked", isBlocked);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
