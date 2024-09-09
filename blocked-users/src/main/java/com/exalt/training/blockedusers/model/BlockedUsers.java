package com.exalt.training.blockedusers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a blocked user in the system.
 * Maps to the "blocked_users" table in the database.
 */
@Data
@Entity
@Table(name = "blocked_users")
@AllArgsConstructor
@NoArgsConstructor
public class BlockedUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blocked_user_seq_gen")
    @SequenceGenerator(name = "blocked_user_seq_gen", sequenceName = "blocked_user_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id; // Unique ID of the blocked user record

    @Column(name = "national_id", nullable = false, unique = true)
    private String nationalId; // National ID of the blocked user

    @Column(name = "username", nullable = false)
    private String userName; // Username of the blocked user

    @Column(name = "created-at", nullable = false)
    private LocalDateTime createdAt; // Time when user has been blocked
}
