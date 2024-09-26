package com.technical.test.shared.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseApiTest {

    @Test
    void testSuccessMethod() {
        String data = "Success data";
        ResponseApi<String> response = ResponseApi.success(data);

        assertNotNull(response);
        assertEquals(data, response.data());
        assertNull(response.message());
    }

    @Test
    void testErrorMethod() {
        String errorMessage = "Error occurred";
        ResponseApi<String> response = ResponseApi.error(errorMessage);

        assertNotNull(response);
        assertNull(response.data());
        assertEquals(errorMessage, response.message());
    }

    @Test
    void testGetters() {
        String data = "Some data";
        String message = "Some message";
        ResponseApi<String> response = new ResponseApi<>(data, message);

        assertEquals(data, response.data());
        assertEquals(message, response.message());
    }
}
