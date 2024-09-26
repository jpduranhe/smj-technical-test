package com.technical.test.auth.service;

import com.technical.test.auth.domain.model.Token;

import java.util.Optional;

/**
 * Interface that defines the token service methods
 * @author Juan Pablo Duran
 * @version 1.0
 * @since 24-09-2024
 */
public interface TokenService {

    /**
     * create  a new user
     * @param email string
     * @return Token
     */
    Token createUserToken(String email );

    /**
     * create  a new user
     * @param jwt String
     * @return Optional Token
     */
    Optional<Token> findByToken(String jwt);
}
