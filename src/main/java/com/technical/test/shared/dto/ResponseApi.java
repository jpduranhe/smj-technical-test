package com.technical.test.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ResponseApi<T>(
        @Schema(description = " data or response", name = "data")
        T data,
        @Schema(description = "Message error", name = "message")
        String message) {
    public static <T> ResponseApi<T> success(T data){
        return new ResponseApi<>(data,null);
    }
    public static <T> ResponseApi<T> error(String message){
        return new ResponseApi<>(null, message);
    }
}
