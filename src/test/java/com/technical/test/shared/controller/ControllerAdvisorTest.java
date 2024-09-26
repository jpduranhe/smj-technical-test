package com.technical.test.shared.controller;

import com.technical.test.shared.dto.ResponseApi;
import com.technical.test.user.domain.exception.InvalidEmailException;
import com.technical.test.user.domain.exception.UserEmailExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerAdvisorTest {

    private ControllerAdvisor controllerAdvisor;

    @BeforeEach
    void setUp() {
        controllerAdvisor = new ControllerAdvisor();
    }

    @Test
    void testOnBadCredentialsException() {
        BadCredentialsException exception = new BadCredentialsException("Invalid credentials");

        ResponseApi<String> response = controllerAdvisor.onBadCredentialsException(exception);

        assertNotNull(response);
        assertEquals("Invalid credentials", response.message());
        assertNull(response.data());
    }

    @Test
    void testOnMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseApi<String> response = controllerAdvisor.onMethodArgumentNotValidException(exception);

        assertNotNull(response);
        assertEquals("field:defaultMessage,", response.message());
        assertNull(response.data());
    }

    @Test
    void testOnUserEmailExistException() {
        UserEmailExistException exception = new UserEmailExistException("Email already exists");

        ResponseApi<String> response = controllerAdvisor.onUserEmailExistException(exception);

        assertNotNull(response);
        assertEquals("Email already exists", response.message());
        assertNull(response.data());
    }

    @Test
    void testOnInvalidEmailException() {
        InvalidEmailException exception = new InvalidEmailException("Invalid email format");

        ResponseApi<String> response = controllerAdvisor.onInvalidEmailException(exception);

        assertNotNull(response);
        assertEquals("Invalid email format", response.message());
        assertNull(response.data());
    }

    @Test
    void testOnRuntimeException() {
        RuntimeException exception = new RuntimeException("Internal server error");

        ResponseApi<String> response = controllerAdvisor.onRuntimeException(exception);

        assertNotNull(response);
        assertEquals("Internal server error", response.message());
        assertNull(response.data());
    }
}
