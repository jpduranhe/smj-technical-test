package com.technical.test.user.mapper;

import com.technical.test.user.domain.model.Phone;
import com.technical.test.user.dto.PhoneDto;
import com.technical.test.user.entity.PhoneEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneMapperTest {

    private final PhoneMapperImpl phoneMapper = new PhoneMapperImpl();

    @Test
    void testToDomainFromPhoneEntity() {
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setCityCode("123");
        phoneEntity.setCountryCode("456");
        phoneEntity.setNumber("7890");

        Phone phone = phoneMapper.toDomain(phoneEntity);

        assertNotNull(phone);
        assertEquals("123", phone.getCityCode());
        assertEquals("456", phone.getCountryCode());
        assertEquals("7890", phone.getNumber());
    }

    @Test
    void testToDomainFromPhoneDto() {
        PhoneDto phoneDto = new PhoneDto("123", "456", "7890");

        Phone phone = phoneMapper.toDomain(phoneDto);

        assertNotNull(phone);
        assertEquals("123", phone.getCityCode());
        assertEquals("456", phone.getCountryCode());
        assertEquals("7890", phone.getNumber());
    }

    @Test
    void testToEntity() {
        Phone phone = new Phone();
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");

        PhoneEntity phoneEntity = phoneMapper.toEntity(phone);

        assertNotNull(phoneEntity);
        assertEquals("123", phoneEntity.getCityCode());
        assertEquals("456", phoneEntity.getCountryCode());
        assertEquals("7890", phoneEntity.getNumber());
    }

    @Test
    void testToDto() {
        Phone phone = new Phone();
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");

        PhoneDto phoneDto = phoneMapper.toDto(phone);

        assertNotNull(phoneDto);
        assertEquals("123", phoneDto.cityCode());
        assertEquals("456", phoneDto.countryCode());
        assertEquals("7890", phoneDto.number());
    }
}
