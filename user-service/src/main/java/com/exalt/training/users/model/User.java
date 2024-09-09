package com.exalt.training.users.model;

import com.exalt.training.users.enums.Rule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


/**
 * Entity representing a User in the system.
 * Maps to the "users" table in the database.
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Integer id; // Unique ID of the user, auto-generated using a sequence generator

    @NotNull(message = "National ID is required.")
    @Pattern(regexp = "\\d{9}", message = "National ID must be exactly 9 digits.")
    @Column(name = "national_id", unique = true, nullable = false)
    private String nationalId; // National ID of the user, unique and 9 digits long, stored in "national_id" column

    @NotBlank(message = "Username cannot be blank.")
    @NotNull(message = "Username cannot be null.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long.")
    @Column(name = "username", nullable = false)
    private String username; // Username of the user, required, case-insensitive, and unique


    @NotNull(message = "Email is required.")
    @Email(message = "Email should be valid.")
    @Column(name = "email", nullable = false)
    private String email; // Email address of the user, stored in "email" column, required and unique

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Column(name = "password", nullable = false)
    private String password; // Password of the user, stored in "password" column, required and must be at least 8 characters long

    @NotBlank(message = "Phone number cannot be blank.")
    @NotNull(message = "Phone number cannot be null.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // Phone number of the user, stored in "phone_number" column, required and unique

    @NotNull(message = "Rule is required.")
    @Column(name = "rule", nullable = false)
    private Rule rule; // User's subscription type or role, stored in "rule" column, must be one of the defined Rule enum values

    @NotNull(message = "Balance can not be null.")
    @Column(name = "balance")
    private Double balance; // Balance in the user's account, stored in "balance" column, optional and can be null

    @Column(nullable = false)
    private boolean isLoggedIn = false; // Indicates whether the user is currently logged in

    @Column(name = "failed_attempts")
    private int failedAttempts=0; // Number of failed login attempts
}
