package com.technical.test.auth.repository;

import com.technical.test.auth.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    Optional<TokenEntity> findByToken(String token);

    List<TokenEntity> findAllValidTokenByUserIdAndIsExpiredFalseAndIsRevokedFalse(String userId);
}
