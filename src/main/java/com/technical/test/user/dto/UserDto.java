package com.technical.test.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.List;

@Schema(description = " Dta", name = "UserDto")public record UserDto(
        @Schema(description = " Email of user", name = "email", example = "juan@rodriguez.org")
        @Email
        String email,

        @Schema(description = " Name of user", name = "name", example = "Juan Rodriguez")
        @NotBlank
        String name,

        @Schema(description = " Password of user", name = "password", example = "hunter2")
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must have at least 8 characters, one uppercase letter, one lowercase letter and one number")
        String password,

        @Schema(description = "List of phone user", name = "phones")
        @NotEmpty @NotNull List<PhoneDto> phones
    ) implements Serializable {
}
