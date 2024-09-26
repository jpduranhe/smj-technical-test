package com.technical.test.shared.controller;

import com.technical.test.shared.dto.ResponseApi;
import com.technical.test.user.domain.exception.InvalidEmailException;
import com.technical.test.user.domain.exception.UserEmailExistException;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {



    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schemaProperties = {
                                    @SchemaProperty(name = "message", schema = @Schema(type = "string",example="Error"))
                            })
            )
    })
    public ResponseApi<String> onBadCredentialsException(BadCredentialsException e) {

        return new ResponseApi<>(null, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schemaProperties = {
                                    @SchemaProperty(name = "message", schema = @Schema(type = "string",example="Error"))
                            })
            )
    })
    public ResponseApi<String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorsString = new StringBuilder();

        e.getBindingResult().getFieldErrors().forEach(objectError -> {
            errorsString.append(objectError.getField()).append(":").append(objectError.getDefaultMessage()).append(",");
        });

        return new ResponseApi<>(null, errorsString.toString());
    }

    @ExceptionHandler(UserEmailExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schemaProperties = {
                                    @SchemaProperty(name = "message", schema = @Schema(type = "string",example="Error"))
                            })
            )
    })
    public ResponseApi<String> onUserEmailExistException(UserEmailExistException e) {

        return new ResponseApi<>(null, e.getMessage());
    }
    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schemaProperties = {
                                    @SchemaProperty(name = "message", schema = @Schema(type = "string",example="Error"))
                            })
            )
    })
    public ResponseApi<String> onInvalidEmailException(InvalidEmailException e) {

        return new ResponseApi<>(null, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schemaProperties = {
                                    @SchemaProperty(name = "message", schema = @Schema(type = "string",example="Error"))
                            })
            )
    })
    public ResponseApi<String> onRuntimeException(RuntimeException e) {

        return new ResponseApi<>(null, e.getMessage());
    }


}
