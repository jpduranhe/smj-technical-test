package com.technical.test.auth.service;

import com.technical.test.auth.service.impl.JwtServiceImpl;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.domain.vo.Email;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private Claims claims;

    private String secretKey = "testSecretKey12345678901234567890123456789012"; // 32-byte key
    private long refreshExpiration = 3600000; // 1 hour

    private User user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", refreshExpiration);

        user = new User();
        user.setId("1");
        user.setEmail(Email.of("mail@email.com"));
        user.setName("John Doe");
        user.setPassword("password123");
        user.setPhones(Arrays.asList());
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(user);
        String username = jwtService.extractUsername(token);

        assertEquals(user.getEmail().getValue(), username);
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(user);

        assertTrue(jwtService.isTokenValid(token, user));
    }



}
