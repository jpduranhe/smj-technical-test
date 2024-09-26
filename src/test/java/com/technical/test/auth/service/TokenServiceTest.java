package com.technical.test.auth.service;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.entity.TokenEntity;
import com.technical.test.auth.mapper.TokenMapper;
import com.technical.test.auth.repository.TokenRepository;
import com.technical.test.auth.service.impl.TokenServiceImpl;
import com.technical.test.user.domain.exception.UserNotFoundException;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.domain.vo.Email;
import com.technical.test.user.entity.UserEntity;
import com.technical.test.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private UserService userService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenMapper tokenMapper;

    private User user;
    private TokenEntity tokenEntity;
    private Token token;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setEmail(Email.of("mail@email.com"));
        user.setName("John Doe");
        user.setPassword("password123");
        user.setPhones(Arrays.asList());

        tokenEntity = TokenEntity.builder()
                .user(new UserEntity(user.getId()))
                .token("jwtToken")
                .isExpired(false)
                .isRevoked(false)
                .build();

        token = new Token();
        token.setToken("jwtToken");
    }

    @Test
    void testCreateUserToken() {
        when(userService.findByEmail(user.getEmail().getValue())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        when(tokenRepository.save(any(TokenEntity.class))).thenReturn(tokenEntity);
        when(tokenMapper.toDomain(tokenEntity)).thenReturn(token);

        Token result = tokenService.createUserToken(user.getEmail().getValue());

        assertNotNull(result);
        assertEquals("jwtToken", result.getToken());
        verify(tokenRepository, times(1)).save(any(TokenEntity.class));
    }

    @Test
    void testCreateUserToken_UserNotFound() {
        when(userService.findByEmail(user.getEmail().getValue())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> tokenService.createUserToken(user.getEmail().getValue()));
    }

    @Test
    void testFindByToken() {
        when(tokenRepository.findByToken("jwtToken")).thenReturn(Optional.of(tokenEntity));
        when(tokenMapper.toDomain(tokenEntity)).thenReturn(token);

        Optional<Token> result = tokenService.findByToken("jwtToken");

        assertTrue(result.isPresent());
        assertEquals("jwtToken", result.get().getToken());
    }

    @Test
    void testFindByToken_NotFound() {
        when(tokenRepository.findByToken("jwtToken")).thenReturn(Optional.empty());

        Optional<Token> result = tokenService.findByToken("jwtToken");

        assertFalse(result.isPresent());
    }


}
