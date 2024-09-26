package com.technical.test.auth.service.impl;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.dto.AuthRequest;
import com.technical.test.auth.service.AuthService;
import com.technical.test.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    @Override
    public Token authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        return tokenService.createUserToken(request.email());
    }
}
