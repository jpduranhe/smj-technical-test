package com.technical.test.user.entity;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testGettersAndSetters() {
        UserEntity user = new UserEntity();
        PhoneEntity phone1 = new PhoneEntity();
        phone1.setId("1");
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");
        PhoneEntity phone2 = new PhoneEntity();
        phone2.setId("2");
        phone2.setCityCode("321");
        phone2.setCountryCode("654");
        phone2.setNumber("0987");
        List<PhoneEntity> phones = Arrays.asList(phone1, phone2);

        user.setId("1");
        user.setName("John Doe");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setPhones(phones);
        Date now = new Date();
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);

        assertEquals("1", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(phones, user.getPhones());
        assertEquals(now, user.getCreated());
        assertEquals(now, user.getModified());
        assertEquals(now, user.getLastLogin());
    }

    @Test
    void testToString() {
        UserEntity user = new UserEntity();
        PhoneEntity phone1 = new PhoneEntity();
        phone1.setId("1");
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");
        PhoneEntity phone2 = new PhoneEntity();
        phone2.setId("2");
        phone2.setCityCode("321");
        phone2.setCountryCode("654");
        phone2.setNumber("0987");
        List<PhoneEntity> phones = Arrays.asList(phone1, phone2);

        user.setId("1");
        user.setName("John Doe");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setPhones(phones);
        user.setIsActive(true);
        Date now = new Date();
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);

        String expected = "UserEntity(id=1, name=John Doe, email=test@example.com, password=password123, phones=[PhoneEntity(id=1, cityCode=123, countryCode=456, number=7890), PhoneEntity(id=2, cityCode=321, countryCode=654, number=0987)], created=" + now + ", modified=" + now + ", lastLogin=" + now + ", isActive=true)";
        assertEquals(expected, user.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        UserEntity user1 = new UserEntity();
        PhoneEntity phone1 = new PhoneEntity();
        phone1.setId("1");
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");
        PhoneEntity phone2 = new PhoneEntity();
        phone2.setId("2");
        phone2.setCityCode("321");
        phone2.setCountryCode("654");
        phone2.setNumber("0987");
        List<PhoneEntity> phones1 = Arrays.asList(phone1, phone2);

        user1.setId("1");
        user1.setName("John Doe");
        user1.setEmail("test@example.com");
        user1.setPassword("password123");
        user1.setPhones(phones1);
        user1.setIsActive(true);
        Date now = new Date();
        user1.setCreated(now);
        user1.setModified(now);
        user1.setLastLogin(now);

        UserEntity user2 = new UserEntity();
        List<PhoneEntity> phones2 = Arrays.asList(phone1, phone2);

        user2.setId("1");
        user2.setName("John Doe");
        user2.setEmail("test@example.com");
        user2.setPassword("password123");
        user2.setPhones(phones2);
        user2.setCreated(now);
        user2.setModified(now);
        user2.setLastLogin(now);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testPhoneRelationship() {
        UserEntity user = new UserEntity();
        PhoneEntity phone = new PhoneEntity();
        phone.setId("1");
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");
        phone.setUser(user);
        user.setPhones(Arrays.asList(phone));

        assertEquals(user, phone.getUser());
        assertEquals(1, user.getPhones().size());
        assertEquals(phone, user.getPhones().get(0));
    }
}
