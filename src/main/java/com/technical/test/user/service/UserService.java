package com.technical.test.user.service;

import com.technical.test.user.domain.model.User;

import java.util.Optional;

/**
 * Interface that defines the user service methods
 * @author Juan Pablo Duran
 * @version 1.0
 * @since 24-09-2024
 */
public interface UserService {
    /**
     * create  a new user
     * @param user object User
     * @return User
     */
    User createUser(User user);

    /**
     * return if existe  a user
     * @param userEmail email of User
     * @return User
     */
    Optional<User> findByEmail(String userEmail);
}
