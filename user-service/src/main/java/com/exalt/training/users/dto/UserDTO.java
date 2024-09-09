package com.exalt.training.users.dto;

import com.exalt.training.users.enums.Rule;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for User.
 * Used to transfer user data between processes and validate input.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @NotNull(message = "National ID is required.")
    @Pattern(regexp = "\\d{9}", message = "National ID must be exactly 9 digits.")
    private String nationalId; // National ID of the user, unique and 9 digits long

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long.")
    private String username; // Username of the user, required and must be unique


    @NotNull(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email; // Email address of the user, required and must be in valid email format

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password; // Password of the user, required and must be at least 8 characters long

    @NotNull(message = "Phone number can not be null.")
    @NotBlank(message = "Phone number is required, it can not be blank.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    private String phoneNumber; // Phone number of the user, required and must be unique


    @NotNull(message = "Rule is required.")
    private Rule rule; // User's subscription type or role, must be one of the defined Rule enum values

    @NotNull(message = "Balance can not be null.")
    private Double balance; // Balance in the user's account
}
