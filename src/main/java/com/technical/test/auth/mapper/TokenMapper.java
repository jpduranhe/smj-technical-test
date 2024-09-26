package com.technical.test.auth.mapper;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.dto.TokenResponse;
import com.technical.test.auth.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    @Mapping(target = "user.email.value",source = "user.email")
    @Mapping(target = "user.phones",ignore = true)
    Token toDomain(TokenEntity token);
    @Mapping(target = "accessToken",source = "token")
    TokenResponse toDto(Token token);
}
