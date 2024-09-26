package com.technical.test.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record UserCreatedDto(
        @Schema(description = " id of user", name = "id", example = "821bef40-0e89-4c48-bc29-848595735a2c")
        @Email
        String id,

        @Schema(description = " date creation  of the user", name = "created", example = "2024-01-25")
        LocalDateTime created,

        @Schema(description = "date last modification", name = "modified", example = "2024-03-02")
        LocalDateTime modified,

        @Schema(description = "date and time last login", name = "password", example = "2024-03-02 15:00:00")
        LocalDateTime lastLogin,

        @Schema(description = "token for User", name = "token", example = "Jawwer")
        String token,

        @Schema(description = "indicate if user is active", name = "active", example = "true")
        boolean isActive
    ) implements Serializable {
}
