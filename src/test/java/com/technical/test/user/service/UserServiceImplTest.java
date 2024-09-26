package com.technical.test.user.service;

import com.github.javafaker.Faker;
import com.technical.test.user.domain.exception.UserEmailExistException;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.domain.vo.Email;
import com.technical.test.user.entity.UserEntity;
import com.technical.test.user.mapper.UserMapper;
import com.technical.test.user.repository.UserRepository;
import com.technical.test.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserServiceImpl userService;

    private Faker faker;
    private String email;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker=new Faker();
        email = faker.internet().emailAddress();

        when(passwordEncoder.encode(anyString())).thenReturn("password");
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        User user = new User();

        user.setEmail(new Email(email));

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(UserEmailExistException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testCreateUser_Success() {
        User user = new User();
        user.setEmail(new Email(email));
        user.setPhones(Collections.emptyList());

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPhones(Collections.emptyList());

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(email, createdUser.getEmail().getValue());
        verify(userRepository, times(1)).existsByEmail(email);
        verify(userRepository, times(2)).save(userEntity);
        verify(userMapper, times(1)).toEntity(user);
        verify(userMapper, times(1)).toDomain(userEntity);
    }

    @Test
    void testCreateUser_RuntimeException() {
        User user = new User();
        user.setEmail(new Email(email));

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userMapper.toEntity(user)).thenThrow(new RuntimeException("Error creating user"));

        assertThrows(RuntimeException.class, () -> userService.createUser(user));
        verify(userRepository, times(1)).existsByEmail(email);
        verify(userMapper, times(1)).toEntity(user);
    }
}
