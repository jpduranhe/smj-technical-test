package com.technical.test.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.dto.AuthRequest;
import com.technical.test.auth.dto.TokenResponse;
import com.technical.test.auth.mapper.TokenMapper;
import com.technical.test.auth.service.AuthService;
import com.technical.test.auth.service.JwtService;
import com.technical.test.auth.service.TokenService;
import com.technical.test.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {


    @MockBean
    private AuthService authService;
    @MockBean
    public UserService userService;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private  TokenMapper tokenMapper;

    private String urlBase = "/auth/login";
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper jsonMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    private Token token;

    @BeforeEach
    void setUp() {
        token = new Token();
        token.setToken("token");

        when(tokenMapper.toDto(any(Token.class))).thenReturn(new TokenResponse("token"));
    }

    @Test
    void authenticate() throws Exception {
        when(authService.authenticate(any(AuthRequest.class))).thenReturn(token);
        AuthRequest request = new AuthRequest("test@mail.com", "test");
        mockMvc.perform(MockMvcRequestBuilders
                        .post(urlBase)
                        .content(jsonMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists());
        verify(authService).authenticate(any(AuthRequest.class));
    }
}
