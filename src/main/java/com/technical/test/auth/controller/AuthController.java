package com.technical.test.auth.controller;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.dto.AuthRequest;
import com.technical.test.auth.dto.TokenResponse;
import com.technical.test.auth.mapper.TokenMapper;
import com.technical.test.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenMapper tokenMapper;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest request) {
       Token token = authService.authenticate(request);
        final TokenResponse response=tokenMapper.toDto(token);
        return ResponseEntity.ok(response);
    }


}
