package com.technical.test.user.service.impl;

import com.technical.test.user.domain.exception.UserEmailExistException;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.entity.PhoneEntity;
import com.technical.test.user.entity.UserEntity;
import com.technical.test.user.mapper.UserMapper;
import com.technical.test.user.repository.UserRepository;
import com.technical.test.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final UserMapper userMapper;

    @NonNull
    private final PasswordEncoder passwordEncode;

    @Override
    @Transactional
    public User createUser(User user) {
        try {
            if (userRepository.existsByEmail(user.getEmail().getValue())) {
                throw new UserEmailExistException("Email already exists");
            }
            UserEntity userEntity = userMapper.toEntity(user);
            userEntity.setPassword(passwordEncode.encode(userEntity.getPassword()));
            userEntity.setIsActive(true);
            List<PhoneEntity> phoneEntities = userEntity.getPhones();
            userEntity.setPhones(null);
            UserEntity userEntitySaved = userRepository.save(userEntity);

            List<PhoneEntity> phoneEntitiesWithUser = phoneEntities.stream().map(phoneEntity -> {
                phoneEntity.setUser(userEntitySaved);
                return phoneEntity;
            }).collect(Collectors.toList());
            userEntitySaved.setPhones(phoneEntitiesWithUser);
            userEntitySaved.setLastLogin(userEntitySaved.getCreated());
            UserEntity userEntitySavedWhitPhones = userRepository.save(userEntitySaved);

            return userMapper.toDomain(userEntitySavedWhitPhones);
        } catch (UserEmailExistException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Error creating user:"+e.getMessage());
        }
    }

    /**
     * return if existe  a user
     *
     * @param userEmail email of User
     * @return User
     */
    @Override
    public Optional<User> findByEmail(String userEmail) {
        try {
            Optional<UserEntity> userEntity =  userRepository.findByEmail(userEmail);
            return userEntity.map(userMapper::toDomain);
        } catch (Exception e) {
            throw new RuntimeException("Error loading user:"+e.getMessage());
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .build();
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error loading user:"+e.getMessage());
        }
    }
}
