package com.technical.test.user.mapper;

import com.technical.test.user.domain.model.Phone;
import com.technical.test.user.dto.PhoneDto;
import com.technical.test.user.entity.PhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "user.email.value",source = "user.email")
    @Mapping(target = "user.phones",ignore = true)
    Phone toDomain(PhoneEntity phoneEntity);
    Phone toDomain(PhoneDto phoneDto);

    @Mapping(target = "user.email",source = "user.email.value")
    PhoneEntity toEntity(Phone phone);
    PhoneDto toDto(Phone phone);

}
