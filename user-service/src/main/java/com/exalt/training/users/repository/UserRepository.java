package com.exalt.training.users.repository;

import com.exalt.training.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom queries for {@link User}.
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * Finds a user by their email.
     *
     * @param email the email address of the user to find.
     * @return an {@link Optional} containing the found {@link User} if present, or {@link Optional#empty()} if not.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their nationalId.
     *
     * @param nationalId the email address of the user to find.
     * @return an {@link Optional} containing the found {@link User} if present, or {@link Optional#empty()} if not.
     */
    Optional<User> findByNationalId (String nationalId);
}
