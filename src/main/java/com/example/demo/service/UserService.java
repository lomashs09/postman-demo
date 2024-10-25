package com.example.demo.service;

import com.example.demo.db.model.User;
import com.example.demo.db.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user in the system.
     *
     * @param user The user entity to be created.
     * @return The created user entity.
     */
    public User createUser(User user) {
        logger.info("Creating user: {}", user);
        return userRepository.save(user);
    }

    /**
     * Fetches all users from the system.
     *
     * @return A list of all user entities.
     */
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    /**
     * Fetches a user by their ID.
     *
     * @param id The ID of the user to be fetched.
     * @return An Optional containing the user entity if found, or empty if not found.
     */
    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        return userRepository.findById(id);
    }

    /**
     * Updates an existing user in the system.
     *
     * @param id The ID of the user to be updated.
     * @param userDetails The new details for the user.
     * @return The updated user entity.
     * @throws RuntimeException if the user with the given ID is not found.
     */
    public User updateUser(Long id, User userDetails) {
        logger.info("Updating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new RuntimeException("User not found with id " + id);
                });

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setUpdatedAt(userDetails.getUpdatedAt());

        logger.info("User updated: {}", user);
        return userRepository.save(user);
    }

    /**
     * Deletes a user from the system.
     *
     * @param id The ID of the user to be deleted.
     * @throws RuntimeException if the user with the given ID is not found.
     */
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new RuntimeException("User not found with id " + id);
                });
        userRepository.delete(user);
        logger.info("User deleted: {}", user);
    }
}
