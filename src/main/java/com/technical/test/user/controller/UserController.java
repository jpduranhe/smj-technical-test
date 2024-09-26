package com.technical.test.user.controller;

import com.technical.test.auth.domain.model.Token;
import com.technical.test.auth.service.TokenService;
import com.technical.test.shared.dto.ResponseApi;
import com.technical.test.user.domain.model.User;
import com.technical.test.user.dto.UserCreatedDto;
import com.technical.test.user.dto.UserDto;
import com.technical.test.user.mapper.UserMapper;
import com.technical.test.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;

    @NonNull
    private final TokenService tokenService;

    @NonNull
    private final UserMapper mapper;


    @PostMapping
    @Operation(description = "Create a User", operationId = "createUser", method = "POST")
    public ResponseEntity<ResponseApi<UserCreatedDto>> createUser(@RequestBody @Valid UserDto userDto){
        User user =mapper.toDomain(userDto);
        User userCreated=  userService.createUser(user);
        UserCreatedDto userCreatedDto = mapper.toDto(userCreated);
        Token token= tokenService.createUserToken(user.getEmail().getValue());
        UserCreatedDto userCreatedDtoWithToken= userCreatedDto.toBuilder().token(token.getToken()).build();

        return ResponseEntity.created(URI.create("/users/"+userCreated.getId()))
                .body(ResponseApi.success(userCreatedDtoWithToken));
    }
}
