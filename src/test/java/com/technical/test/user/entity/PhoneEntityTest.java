
package com.technical.test.user.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneEntityTest {

    @Test
    void testGettersAndSetters() {
        PhoneEntity phone = new PhoneEntity();
        phone.setId("1");
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");

        assertEquals("1", phone.getId());
        assertEquals("123", phone.getCityCode());
        assertEquals("456", phone.getCountryCode());
        assertEquals("7890", phone.getNumber());
    }

    @Test
    void testToString() {
        PhoneEntity phone = new PhoneEntity();
        phone.setId("1");
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");

        String expected = "PhoneEntity(id=1, cityCode=123, countryCode=456, number=7890)";
        assertEquals(expected, phone.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        PhoneEntity phone1 = new PhoneEntity();
        phone1.setId("1");
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");

        PhoneEntity phone2 = new PhoneEntity();
        phone2.setId("1");
        phone2.setCityCode("123");
        phone2.setCountryCode("456");
        phone2.setNumber("7890");

        PhoneEntity phone3 = new PhoneEntity();
        phone3.setId("2");
        phone3.setCityCode("321");
        phone3.setCountryCode("654");
        phone3.setNumber("0987");

        assertEquals(phone1, phone2);
        assertNotEquals(phone1, phone3);
        assertEquals(phone1.hashCode(), phone2.hashCode());
        assertNotEquals(phone1.hashCode(), phone3.hashCode());
    }

    @Test
    void testUserRelationship() {
        PhoneEntity phone = new PhoneEntity();
        UserEntity user = new UserEntity();
        user.setId("1");
        phone.setUser(user);

        assertEquals(user, phone.getUser());
    }
}
