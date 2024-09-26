package com.technical.test.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record PhoneDto(
        @Schema(description = " Code of city", name = "cityCode", example = "1")
        @NotBlank  String cityCode,
        @Schema(description = "code of country", name = "countryCode", example = "57")
        @NotBlank String countryCode,
        @Schema(description = "number of phone", name = "number", example = "1234567")
        @NotBlank String number
        ) implements Serializable {
}
