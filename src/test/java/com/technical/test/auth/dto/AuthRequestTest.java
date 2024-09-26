package com.technical.test.auth.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {
    private ObjectMapper objectMapper;
    private Validator validator;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testAuthRequestToJson() throws Exception {
        AuthRequest authRequest = new AuthRequest("mail@email.com", "password123");

        String jsonString = objectMapper.writeValueAsString(authRequest);

        assertNotNull(jsonString);
        assertTrue(jsonString.contains("\"email\":\"mail@email.com\""));
        assertTrue(jsonString.contains("\"password\":\"password123\""));
    }

    @Test
    void testAuthRequestFromJson() throws Exception {
        String jsonString = "{\"email\":\"mail@email.com\",\"password\":\"password123\"}";

        AuthRequest authRequest = objectMapper.readValue(jsonString, AuthRequest.class);

        assertNotNull(authRequest);
        assertEquals("mail@email.com", authRequest.email());
        assertEquals("password123", authRequest.password());
    }

    @Test
    void testAuthRequestValidation() {
        AuthRequest validAuthRequest = new AuthRequest("mail@email.com", "password123");
        Set<ConstraintViolation<AuthRequest>> violations = validator.validate(validAuthRequest);
        assertTrue(violations.isEmpty());

        AuthRequest invalidAuthRequest = new AuthRequest("", "");
        violations = validator.validate(invalidAuthRequest);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }
}
