package com.technical.test.auth.service.impl;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.entity.TokenEntity;
import com.technical.test.auth.mapper.TokenMapper;
import com.technical.test.auth.repository.TokenRepository;
import com.technical.test.auth.service.TokenService;
import com.technical.test.auth.service.JwtService;
import com.technical.test.user.domain.exception.UserNotFoundException;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.entity.UserEntity;
import com.technical.test.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final TokenMapper tokenMapper;


    /**
     * create  a new user
     * @param email string
     * @return Token
     */
    @Override
    public Token createUserToken(String email ) {
        try{
            User userForToken = userService.findByEmail(email)
                    .orElseThrow(()->new UserNotFoundException(email));
            String accessToken = jwtService.generateToken(userForToken);
            revokeAllUserTokens(userForToken);
            return saveUserToken(userForToken, accessToken);
        }catch (UserNotFoundException e){
            throw e;
        }catch (Exception e){
            log.error( e.getMessage());
            throw new RuntimeException("Error saving token");
        }

    }

    /**
     * create  a new user
     * @param jwt String
     * @return Optional Token
     */
    @Override
    public Optional<Token> findByToken(String jwt) {
        try{
            return tokenRepository.findByToken(jwt).map(tokenMapper::toDomain);
        }catch (Exception e){
            log.error( e.getMessage());
            throw new RuntimeException("Error finding token");
        }
    }

    private void revokeAllUserTokens(final User user) {
        try{
             List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokenByUserIdAndIsExpiredFalseAndIsRevokedFalse(user.getId());
            if (!validUserTokens.isEmpty()) {
                validUserTokens.forEach(token -> {
                    token.setIsExpired(true);
                    token.setIsRevoked(true);
                });
                tokenRepository.saveAll(validUserTokens);
            }
        }catch (Exception e){
            throw new RuntimeException("Error saving token");
        }
    }
    private Token saveUserToken(User user, String jwtToken) {
        TokenEntity token = TokenEntity.builder()
                .user(new UserEntity(user.getId()))
                .token(jwtToken)
                .isExpired(false)
                .isRevoked(false)
                .build();

        TokenEntity tokenSaved =  tokenRepository.save(token);
        return tokenMapper.toDomain(tokenSaved);
    }

}
