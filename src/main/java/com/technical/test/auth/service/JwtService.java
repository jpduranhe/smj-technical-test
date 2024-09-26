package com.technical.test.auth.service;

import com.technical.test.user.domain.model.User;

/**
 * Interface that defines the Jwt service methods
 * @author Juan Pablo Duran
 * @version 1.0
 * @since 24-09-2024
 */
public interface JwtService {

    /**
     * get the username from the token
     * @param token String
     * @return String
     */
    String extractUsername(String token);

    /**
     * generate a token
     * @param user User
     * @return String
     */
    String generateToken(User user);

    /**
     * validate the token is valid for a user
     * @param token String
     * @param user User
     * @return boolean
     */
    boolean isTokenValid(String token, User user);
}
