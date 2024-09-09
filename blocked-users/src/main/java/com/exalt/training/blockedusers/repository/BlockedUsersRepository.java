package com.exalt.training.blockedusers.repository;
import com.exalt.training.blockedusers.model.BlockedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link BlockedUsers} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom queries for {@link BlockedUsers}.
 */
public interface BlockedUsersRepository extends JpaRepository<BlockedUsers,Integer> {
    /**
     * Finds a blocked users by their nationalId.
     *
     * @param nationalId the nationalId address of the BlockedUser to find.
     * @return an {@link Optional} containing the found {@link BlockedUsers} if present, or {@link Optional#empty()} if not.
     */
    Optional<BlockedUsers> findByNationalId(String nationalId);
}