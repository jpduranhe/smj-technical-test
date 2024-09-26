package com.technical.test.user.domain.model;

import com.technical.test.user.domain.vo.Email;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        Email email = new Email("test@example.com");
        Phone phone1 = new Phone();
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");
        Phone phone2 = new Phone();
        phone2.setCityCode("321");
        phone2.setCountryCode("654");
        phone2.setNumber("0987");
        List<Phone> phones = Arrays.asList(phone1, phone2);

        user.setId("1");
        user.setEmail(email);
        user.setName("John Doe");
        user.setPassword("password123");
        user.setPhones(phones);

        assertEquals("1", user.getId());
        assertEquals(email, user.getEmail());
        assertEquals("John Doe", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals(phones, user.getPhones());
    }

    @Test
    void testToString() {
        User user = new User();
        Email email = new Email("test@example.com");
        Phone phone1 = new Phone();
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");
        Phone phone2 = new Phone();
        phone2.setCityCode("321");
        phone2.setCountryCode("654");
        phone2.setNumber("0987");
        List<Phone> phones = Arrays.asList(phone1, phone2);

        user.setId("1");
        user.setEmail(email);
        user.setName("John Doe");
        user.setPassword("password123");
        user.setPhones(phones);

        String expected = "User(id=1, email=Email{value='test@example.com'}, name=John Doe, password=password123, phones=[Phone(cityCode=123, countryCode=456, number=7890, user=null), Phone(cityCode=321, countryCode=654, number=0987, user=null)], created=null, modified=null, lastLogin=null, isActive=null)";
        assertEquals(expected, user.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User();
        Email email1 = new Email("test@example.com");
        Phone phone1 = new Phone();
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");
        Phone phone2 = new Phone();
        phone2.setCityCode("321");
        phone2.setCountryCode("654");
        phone2.setNumber("0987");
        List<Phone> phones1 = Arrays.asList(phone1, phone2);

        user1.setId("1");
        user1.setEmail(email1);
        user1.setName("John Doe");
        user1.setPassword("password123");
        user1.setPhones(phones1);

        User user2 = new User();
        Email email2 = new Email("test@example.com");
        List<Phone> phones2 = Arrays.asList(phone1, phone2);

        user2.setId("1");
        user2.setEmail(email2);
        user2.setName("John Doe");
        user2.setPassword("password123");
        user2.setPhones(phones2);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
