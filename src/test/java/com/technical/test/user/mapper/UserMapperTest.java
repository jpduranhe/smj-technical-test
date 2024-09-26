package com.technical.test.user.mapper;

import com.technical.test.user.domain.model.User;
import com.technical.test.user.domain.model.Phone;
import com.technical.test.user.domain.vo.Email;
import com.technical.test.user.dto.UserCreatedDto;
import com.technical.test.user.dto.UserDto;
import com.technical.test.user.dto.PhoneDto;
import com.technical.test.user.entity.UserEntity;
import com.technical.test.user.entity.PhoneEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMapperImplTest {

    @Mock
    private PhoneMapper phoneMapper;

    @InjectMocks
    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDomainFromUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setName("John Doe");
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password123");
        userEntity.setCreated(new Date());
        userEntity.setModified(new Date());
        userEntity.setLastLogin(new Date());
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setCityCode("123");
        phoneEntity.setCountryCode("456");
        phoneEntity.setNumber("7890");
        userEntity.setPhones(Arrays.asList(phoneEntity));

        Phone phone = new Phone();
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");
        when(phoneMapper.toDomain(phoneEntity)).thenReturn(phone);

        User user = userMapper.toDomain(userEntity);

        assertNotNull(user);
        assertEquals("1", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("test@example.com", user.getEmail().getValue());
        assertEquals("password123", user.getPassword());
        assertEquals(1, user.getPhones().size());
        assertEquals(phone, user.getPhones().get(0));
        assertNotNull(user.getCreated());
        assertNotNull(user.getModified());
        assertNotNull(user.getLastLogin());
    }

    @Test
    void testToDomainFromUserDto() {
        UserDto userDto = new UserDto("test@example.com", "John Doe", "password123", Arrays.asList(new PhoneDto("123", "456", "7890")));

        Phone phone = new Phone();
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");
        when(phoneMapper.toDomain(any(PhoneDto.class))).thenReturn(phone);

        User user = userMapper.toDomain(userDto);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail().getValue());
        assertEquals("John Doe", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals(1, user.getPhones().size());
        assertEquals(phone, user.getPhones().get(0));
    }

    @Test
    void testToEntity() {
        User user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail(new Email("test@example.com"));
        user.setPassword("password123");
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        Phone phone = new Phone();
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");
        user.setPhones(Arrays.asList(phone));

        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setCityCode("123");
        phoneEntity.setCountryCode("456");
        phoneEntity.setNumber("7890");
        when(phoneMapper.toEntity(phone)).thenReturn(phoneEntity);

        UserEntity userEntity = userMapper.toEntity(user);

        assertNotNull(userEntity);
        assertEquals("1", userEntity.getId());
        assertEquals("John Doe", userEntity.getName());
        assertEquals("test@example.com", userEntity.getEmail());
        assertEquals("password123", userEntity.getPassword());
        assertEquals(1, userEntity.getPhones().size());
        assertEquals(phoneEntity, userEntity.getPhones().get(0));
        assertNotNull(userEntity.getCreated());
        assertNotNull(userEntity.getModified());
        assertNotNull(userEntity.getLastLogin());
    }

    @Test
    void testToDto() {
        User user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail(new Email("test@example.com"));
        user.setPassword("password123");
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setPhones(Collections.emptyList());

        UserCreatedDto userCreatedDto = userMapper.toDto(user);

        assertNotNull(userCreatedDto);
        assertEquals("1", userCreatedDto.id());
        assertEquals(user.getCreated(), userCreatedDto.created());
        assertEquals(user.getModified(), userCreatedDto.modified());
        assertEquals(user.getLastLogin(), userCreatedDto.lastLogin());
        assertNull(userCreatedDto.token());
        assertFalse(userCreatedDto.isActive());
    }
}
