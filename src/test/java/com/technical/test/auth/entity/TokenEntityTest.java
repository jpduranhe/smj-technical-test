package com.technical.test.auth.entity;

import com.technical.test.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenEntityTest {
    private TokenEntity tokenEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setEmail("mail@email.com");
        userEntity.setName("John Doe");
        userEntity.setPassword("password123");

        tokenEntity = TokenEntity.builder()
                .id(1)
                .token("jwtToken")
                .tokenType(TokenEntity.TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .user(userEntity)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, tokenEntity.getId());
        assertEquals("jwtToken", tokenEntity.getToken());
        assertEquals(TokenEntity.TokenType.BEARER, tokenEntity.getTokenType());
        assertFalse(tokenEntity.getIsRevoked());
        assertFalse(tokenEntity.getIsExpired());
        assertEquals(userEntity, tokenEntity.getUser());

        tokenEntity.setId(2);
        tokenEntity.setToken("newToken");
        tokenEntity.setTokenType(TokenEntity.TokenType.BEARER);
        tokenEntity.setIsRevoked(true);
        tokenEntity.setIsExpired(true);
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setId("2");
        tokenEntity.setUser(newUserEntity);

        assertEquals(2, tokenEntity.getId());
        assertEquals("newToken", tokenEntity.getToken());
        assertEquals(TokenEntity.TokenType.BEARER, tokenEntity.getTokenType());
        assertTrue(tokenEntity.getIsRevoked());
        assertTrue(tokenEntity.getIsExpired());
        assertEquals(newUserEntity, tokenEntity.getUser());
    }

    @Test
    void testBuilder() {
        TokenEntity newTokenEntity = TokenEntity.builder()
                .id(3)
                .token("anotherToken")
                .tokenType(TokenEntity.TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .user(userEntity)
                .build();

        assertEquals(3, newTokenEntity.getId());
        assertEquals("anotherToken", newTokenEntity.getToken());
        assertEquals(TokenEntity.TokenType.BEARER, newTokenEntity.getTokenType());
        assertFalse(newTokenEntity.getIsRevoked());
        assertFalse(newTokenEntity.getIsExpired());
        assertEquals(userEntity, newTokenEntity.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        TokenEntity emptyTokenEntity = new TokenEntity();

        assertNull(emptyTokenEntity.getId());
        assertNull(emptyTokenEntity.getToken());
        assertNull(emptyTokenEntity.getIsRevoked());
        assertNull(emptyTokenEntity.getIsExpired());
        assertNull(emptyTokenEntity.getUser());
    }

    @Test
    void testAllArgsConstructor() {
        TokenEntity allArgsTokenEntity = new TokenEntity(4, "fullToken", TokenEntity.TokenType.BEARER, true, true, userEntity);

        assertEquals(4, allArgsTokenEntity.getId());
        assertEquals("fullToken", allArgsTokenEntity.getToken());
        assertEquals(TokenEntity.TokenType.BEARER, allArgsTokenEntity.getTokenType());
        assertTrue(allArgsTokenEntity.getIsRevoked());
        assertTrue(allArgsTokenEntity.getIsExpired());
        assertEquals(userEntity, allArgsTokenEntity.getUser());
    }
}
