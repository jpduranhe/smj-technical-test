package com.technical.test.auth.domain.model;

import com.technical.test.user.domain.model.User;
import com.technical.test.user.domain.vo.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId("1");
        user.setEmail(Email.of("mail@email.com"));
        user.setName("John Doe");
        user.setPassword("password123");

        Token token = new Token();
        token.setId(1);
        token.setToken("jwtToken");
        token.setIsRevoked(false);
        token.setIsExpired(false);
        token.setUser(user);

        assertEquals(1, token.getId());
        assertEquals("jwtToken", token.getToken());
        assertFalse(token.getIsRevoked());
        assertFalse(token.getIsExpired());
        assertEquals(user, token.getUser());

        token.setId(2);
        token.setToken("newToken");
        token.setIsRevoked(true);
        token.setIsExpired(true);
        User newUser = new User();
        newUser.setId("2");
        token.setUser(newUser);

        assertEquals(2, token.getId());
        assertEquals("newToken", token.getToken());
        assertTrue(token.getIsRevoked());
        assertTrue(token.getIsExpired());
        assertEquals(newUser, token.getUser());
    }

    @Test
    void testBuilder() {
        User user = new User();
        user.setId("1");
        user.setEmail(Email.of("mail@email.com"));
        user.setName("John Doe");
        user.setPassword("password123");

        Token token = Token.builder()
                .id(1)
                .token("jwtToken")
                .isRevoked(false)
                .isExpired(false)
                .user(user)
                .build();

        assertEquals(1, token.getId());
        assertEquals("jwtToken", token.getToken());
        assertFalse(token.getIsRevoked());
        assertFalse(token.getIsExpired());
        assertEquals(user, token.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        Token token = new Token();

        assertNull(token.getId());
        assertNull(token.getToken());
        assertNull(token.getIsRevoked());
        assertNull(token.getIsExpired());
        assertNull(token.getUser());
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User();
        user.setId("1");
        user.setEmail(Email.of("mail@email.com"));
        user.setName("John Doe");
        user.setPassword("password123");

        Token token = new Token(1, "jwtToken", false, false, user);

        assertEquals(1, token.getId());
        assertEquals("jwtToken", token.getToken());
        assertFalse(token.getIsRevoked());
        assertFalse(token.getIsExpired());
        assertEquals(user, token.getUser());
    }
}
