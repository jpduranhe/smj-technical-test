package com.technical.test.auth.mapper;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.dto.TokenResponse;
import com.technical.test.auth.entity.TokenEntity;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.domain.vo.Email;
import com.technical.test.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TokenMapperTest {
    private TokenMapperImpl tokenMapper;

    @BeforeEach
    void setUp() {
        tokenMapper = new TokenMapperImpl();
    }

    @Test
    void testToDomain() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setEmail("mail@email.com");
        userEntity.setName("John Doe");
        userEntity.setPassword("password123");
        userEntity.setCreated(new Date());
        userEntity.setModified(new Date());
        userEntity.setLastLogin(new Date());
        userEntity.setIsActive(true);

        TokenEntity tokenEntity = TokenEntity.builder()
                .user(userEntity)
                .id(1)
                .token("jwtToken")
                .isRevoked(false)
                .isExpired(false)
                .build();

        Token token = tokenMapper.toDomain(tokenEntity);

        assertNotNull(token);
        assertEquals("jwtToken", token.getToken());
        assertEquals("1", token.getUser().getId());
        assertEquals("mail@email.com", token.getUser().getEmail().getValue());
        assertEquals("John Doe", token.getUser().getName());
        assertEquals("password123", token.getUser().getPassword());
        assertEquals(userEntity.getCreated().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime(), token.getUser().getCreated());
        assertEquals(userEntity.getModified().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime(), token.getUser().getModified());
        assertEquals(userEntity.getLastLogin().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime(), token.getUser().getLastLogin());
        assertTrue(token.getUser().getIsActive());
    }

    @Test
    void testToDto() {
        User user = new User();
        user.setId("1");
        user.setEmail(Email.of("mail@email.com"));
        user.setName("John Doe");
        user.setPassword("password123");
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsActive(true);

        Token token = Token.builder()
                .user(user)
                .id(1)
                .token("jwtToken")
                .isRevoked(false)
                .isExpired(false)
                .build();

        TokenResponse tokenResponse = tokenMapper.toDto(token);

        assertNotNull(tokenResponse);
        assertEquals("jwtToken", tokenResponse.accessToken());
    }
}
