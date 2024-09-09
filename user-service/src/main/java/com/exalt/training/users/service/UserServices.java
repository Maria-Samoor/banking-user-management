package com.exalt.training.users.service;

import com.exalt.training.users.exception.UnauthorizedException;
import com.exalt.training.users.exception.UserNotFoundException;
import com.exalt.training.users.model.User;
import com.exalt.training.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user-related operations such as checking balance, requesting credit and debit, and logging out.
 */
@Service
@AllArgsConstructor
public class UserServices {
    private final UserRepository userRepository;

    /**
     * Retrieves the user if they are logged in.
     *
     * @param nationalId the national ID of the user to be retrieved.
     * @return the User entity if the user is logged in.
     * @throws UserNotFoundException if the user is not found.
     * @throws UnauthorizedException if the user is not logged in.
     */
    private User getUserIfLoggedIn(String nationalId) {
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if ( !user.isLoggedIn()) {
            throw new UnauthorizedException("User is not logged in");
        }
        return user;
    }

    /**
     * Checks the balance of the user identified by the given national ID.
     *
     * @param nationalId the national ID of the user whose balance is to be checked.
     * @return the User entity containing the balance.
     * @throws UnauthorizedException if the user is not logged in.
     */
    public User checkBalance(String nationalId) {
        return getUserIfLoggedIn(nationalId);
    }

    /**
     * Requests a credit operation for the user identified by the given national ID.
     *
     * @param nationalId the national ID of the user to be credited.
     * @param amount     the amount to be credited.
     * @return the updated User entity with the new balance.
     * @throws UnauthorizedException if the user is not logged in.
     */
    public User requestCredit(String nationalId, double amount) {
        User user = getUserIfLoggedIn(nationalId);
        user.setBalance(user.getBalance() + amount);
        return userRepository.save(user);
    }

    /**
     * Requests a debit operation for the user identified by the given national ID.
     *
     * @param nationalId the national ID of the user to be debited.
     * @param amount     the amount to be debited.
     * @return the updated User entity with the new balance.
     * @throws UnauthorizedException if the user is not logged in.
     * @throws UnauthorizedException if the user has insufficient balance for the debit operation.
     */
    public User requestDebit(String nationalId, double amount) {
        User user = getUserIfLoggedIn(nationalId);
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
        } else {
            throw new UnauthorizedException("Insufficient balance for debit operation");
        }
        return userRepository.save(user);
    }

    /**
     * Logs out the user identified by the given national ID.
     *
     * @param nationalId the national ID of the user to be logged out.
     * @throws UserNotFoundException if the user is not found.
     * @throws IllegalStateException if the user is already logged out.
     */
    public void logoutUser(String nationalId) {
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!user.isLoggedIn()) {
            throw new IllegalStateException("User is already logged out");
        }
        user.setLoggedIn(false);
        userRepository.save(user);
    }
}
