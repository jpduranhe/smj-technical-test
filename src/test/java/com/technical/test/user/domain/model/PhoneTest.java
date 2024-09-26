package com.technical.test.user.domain.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void testGettersAndSetters() {
        Phone phone = new Phone();
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");

        assertEquals("123", phone.getCityCode());
        assertEquals("456", phone.getCountryCode());
        assertEquals("7890", phone.getNumber());
    }

    @Test
    void testToString() {
        Phone phone = new Phone();
        phone.setCityCode("123");
        phone.setCountryCode("456");
        phone.setNumber("7890");

        String expected = "Phone(cityCode=123, countryCode=456, number=7890, user=null)";
        assertEquals(expected, phone.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Phone phone1 = new Phone();
        phone1.setCityCode("123");
        phone1.setCountryCode("456");
        phone1.setNumber("7890");

        Phone phone2 = new Phone();
        phone2.setCityCode("123");
        phone2.setCountryCode("456");
        phone2.setNumber("7890");

        assertEquals(phone1, phone2);
        assertEquals(phone1.hashCode(), phone2.hashCode());
    }
}
