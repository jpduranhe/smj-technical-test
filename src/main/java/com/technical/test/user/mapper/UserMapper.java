package com.technical.test.user.mapper;

import com.technical.test.user.domain.model.User;
import com.technical.test.user.dto.UserCreatedDto;
import com.technical.test.user.dto.UserDto;
import com.technical.test.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PhoneMapper.class})
public interface UserMapper {
    @Mapping(target = "email.value",source = "email")
    User toDomain(UserEntity userEntity);
    @Mapping(target = "email.value",source = "email")
    User toDomain(UserDto userDto);

    @Mapping(target = "email",source = "email.value")
    UserEntity toEntity(User user);

    UserCreatedDto toDto(User user);
}
