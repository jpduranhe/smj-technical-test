package com.technical.test.user.repository;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.technical.test.user.entity.PhoneEntity;
import com.technical.test.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private FakeValuesService fakeValuesService;
    private Faker faker= new Faker();
    @BeforeEach
    void setUp() {
        fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
    }

    @Test
    void test() {



        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(faker.internet().emailAddress());
        userEntity.setName(faker.name().fullName());
        userEntity.setPassword("Aa123456");
        UserEntity userEntitySaved =  userRepository.save(userEntity);


        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setCityCode(faker.regexify("[0-9]{2}"));
        phoneEntity.setCountryCode(faker.regexify("[0-9]{2}"));
        phoneEntity.setNumber(faker.regexify("[0-9]{9}"));
        phoneEntity.setUser(userEntitySaved);
        userEntitySaved.setPhones(List.of(phoneEntity));


        UserEntity userEntitySavedFull= userRepository.save(userEntitySaved);;
        assertNotNull(userEntitySavedFull);
        assertNotNull(userEntitySavedFull.getCreated());
        assertNotNull(userEntitySavedFull.getModified());
        assertFalse(userEntitySavedFull.getPhones().isEmpty());

    }
}
