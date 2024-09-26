package com.technical.test.user.domain.vo;

import com.technical.test.user.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void testConstructorValidEmail() {
        Email email = new Email("test@example.com");
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void testConstructorInvalidEmail() {
        assertThrows(InvalidEmailException.class, () -> new Email("invalid-email"));
    }

    @Test
    void testOfValidEmail() {
        Email email = Email.of("test@example.com");
        assertNotNull(email);
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void testOfNullEmail() {
        Email email = Email.of(null);
        assertNull(email);
    }

    @Test
    void testToString() {
        Email email = new Email("test@example.com");
        String expected = "Email{value='test@example.com'}";
        assertEquals(expected, email.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("test@example.com");
        Email email3 = new Email("different@example.com");

        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
        assertEquals(email1.hashCode(), email2.hashCode());
        assertNotEquals(email1.hashCode(), email3.hashCode());
    }
}
