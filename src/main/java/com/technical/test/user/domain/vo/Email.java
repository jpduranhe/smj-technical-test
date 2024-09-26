package com.technical.test.user.domain.vo;


import com.technical.test.user.domain.exception.InvalidEmailException;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

@EqualsAndHashCode
public class Email implements Serializable, ValueObject<String> {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String EMAIL_INVALIDO = "Email __e__ is invalid";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private String value;


    public Email(String value) {
        validateEmail(value);
        this.value = value;
    }
    public static Email of(String email) {
        return (email!=null)? new Email(email): null;
    }
    private static void validateEmail(String email) {
        if (email == null ) return;
        if ( !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException(EMAIL_INVALIDO.replace("__e__", email));
        }
    }

    @Override
    public String toString() {
        return "Email{value='" + value + "'}";
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
