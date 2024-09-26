package com.technical.test.auth.service;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.dto.AuthRequest;

public interface AuthService {

    Token authenticate(AuthRequest request) ;
}
