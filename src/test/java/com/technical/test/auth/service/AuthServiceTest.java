package com.technical.test.auth.service;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.dto.AuthRequest;
import com.technical.test.auth.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthRequest authRequest;
    private Token token;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest("mail@email.com", "password123");
        token = new Token();
        token.setToken("jwtToken");
    }

    @Test
    void testAuthenticate_Success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(UsernamePasswordAuthenticationToken.class));
        when(tokenService.createUserToken(authRequest.email())).thenReturn(token);

        Token result = authService.authenticate(authRequest);

        assertNotNull(result);
        assertEquals("jwtToken", result.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).createUserToken(authRequest.email());
    }

    @Test
    void testAuthenticate_Failure() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        assertThrows(AuthenticationException.class, () -> authService.authenticate(authRequest));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(0)).createUserToken(authRequest.email());
    }
}
