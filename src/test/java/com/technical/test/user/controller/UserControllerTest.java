package com.technical.test.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.javafaker.Faker;
import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.service.JwtService;
import com.technical.test.auth.service.TokenService;
import com.technical.test.user.domain.exception.UserEmailExistException;
import com.technical.test.user.domain.model.Phone;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.domain.vo.Email;
import com.technical.test.user.dto.PhoneDto;
import com.technical.test.user.dto.UserCreatedDto;
import com.technical.test.user.dto.UserDto;
import com.technical.test.user.mapper.UserMapper;
import com.technical.test.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@test.cl",password = "admin")
class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserMapper mapper;

    private String urlBase = "";

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper jsonMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    private Faker faker = new Faker();
    private UserDto userDto;
    private UserDto userDtoBadEmail;
    private UserDto userDtoBadPassword;
    private UserCreatedDto userCreatedDto;
    private User user;


    @BeforeEach
    void setUp() {
        urlBase = "/users";

        String id=faker.internet().uuid();

        Email email=Email.of(faker.internet().emailAddress());

        String name= faker.name().fullName();

        String password=faker.regexify("[A-Za-z1-9]{8}");
        when(tokenService.createUserToken(anyString())).thenReturn(Token.builder()
                .token("token")
                .isExpired(false)
                .isRevoked(false)
                .build());

        List<Phone> phones= List.of(new Phone("123","456","7890",user));
        List<PhoneDto> phoneDtos= List.of(new PhoneDto("123","456","7890"));
        LocalDateTime created=LocalDateTime.now();

        LocalDateTime modified=LocalDateTime.now();;

        LocalDateTime lastLogin=LocalDateTime.now();;

        boolean isActive = true;

        user= new User(id,email,name,password,phones,created,modified,lastLogin,isActive);
        userDto = new UserDto(email.getValue(),name,password,phoneDtos);
        userDtoBadEmail = new UserDto("bad-email",name,password,phoneDtos);
        userDtoBadPassword = new UserDto("bad-email",name,password.toLowerCase(),phoneDtos);
        userCreatedDto = new UserCreatedDto(id,created,modified,lastLogin,"Jawwer",isActive);

        when(mapper.toDomain(any(UserDto.class))).thenReturn(user);
        when(mapper.toDto(any(User.class))).thenReturn(userCreatedDto);
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(urlBase)
                        .content(jsonMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").doesNotExist());
        verify(userService).createUser(any(User.class));
    }
    @Test
    void createUserExistEmail() throws Exception {
        when(userService.createUser(any(User.class))).thenThrow(new UserEmailExistException("Email already exist"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post(urlBase)
                        .content(jsonMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
        verify(userService).createUser(any(User.class));
    }
    @Test
    void createUserBadEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(urlBase)
                        .content(jsonMapper.writeValueAsString(userDtoBadEmail))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
        verify(userService,never()).createUser(any(User.class));
    }
    @Test
    void createUserBadPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(urlBase)
                        .content(jsonMapper.writeValueAsString(userDtoBadPassword))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
        verify(userService,never()).createUser(any(User.class));
    }

}
