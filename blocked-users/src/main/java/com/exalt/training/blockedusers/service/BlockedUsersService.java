package com.exalt.training.blockedusers.service;
import com.exalt.training.blockedusers.exception.UserBlockedException;
import com.exalt.training.blockedusers.repository.BlockedUsersRepository;
import lombok.AllArgsConstructor;
import com.exalt.training.blockedusers.model.BlockedUsers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class for managing blocked users.
 */
@Service
@AllArgsConstructor
public class BlockedUsersService {
    private final BlockedUsersRepository blockedUsersRepository; // Repository for blocked users

    /**
     * Blocks a user by national ID and username.
     *
     * @param nationalId The national ID of the user.
     * @param userName The username of the user.
     */
    public void blockUser( String nationalId, String userName) {
        if (blockedUsersRepository.findByNationalId(nationalId).isPresent()) {
            throw new UserBlockedException("User is already blocked");
        }
        BlockedUsers blockedUser = new BlockedUsers();
        blockedUser.setNationalId(nationalId);
        blockedUser.setUserName(userName);
        blockedUser.setCreatedAt(LocalDateTime.now());
        blockedUsersRepository.save(blockedUser);
    }

    /**
     * Unblocks a user by national ID.
     *
     * @param nationalId The national ID of the user to unblock.
     */
    public void unblockUser(String nationalId) {
        BlockedUsers blockedUser = blockedUsersRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new IllegalStateException("user not found"));
        blockedUsersRepository.delete(blockedUser);
    }

    /**
     * Checks if a user is blocked by national ID.
     *
     * @param nationalId The national ID of the user.
     * @return True if the user is blocked, false otherwise.
     */
    public boolean isUserBlocked(String nationalId) {
        return blockedUsersRepository.findByNationalId(nationalId).isPresent();
    }

}
