package com.technical.test.auth.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenResponseTest {

    @Test
    void testTokenResponse() {
        String accessToken = "jwtToken";
        TokenResponse tokenResponse = new TokenResponse(accessToken);

        assertNotNull(tokenResponse);
        assertEquals(accessToken, tokenResponse.accessToken());
    }

    @Test
    void testTokenResponseEquality() {
        String accessToken = "jwtToken";
        TokenResponse tokenResponse1 = new TokenResponse(accessToken);
        TokenResponse tokenResponse2 = new TokenResponse(accessToken);

        assertEquals(tokenResponse1, tokenResponse2);
        assertEquals(tokenResponse1.hashCode(), tokenResponse2.hashCode());
    }

    @Test
    void testTokenResponseInequality() {
        TokenResponse tokenResponse1 = new TokenResponse("jwtToken1");
        TokenResponse tokenResponse2 = new TokenResponse("jwtToken2");

        assertNotEquals(tokenResponse1, tokenResponse2);
    }
    @Test
    void testTokenResponseToJson() throws Exception {
        String accessToken = "jwtToken";
        TokenResponse tokenResponse = new TokenResponse(accessToken);
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(tokenResponse);

        assertNotNull(jsonString);
        assertTrue(jsonString.contains("\"access_token\":\"jwtToken\""));
    }

    @Test
    void testTokenResponseFromJson() throws Exception {
        String jsonString = "{\"access_token\":\"jwtToken\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        TokenResponse tokenResponse = objectMapper.readValue(jsonString, TokenResponse.class);

        assertNotNull(tokenResponse);
        assertEquals("jwtToken", tokenResponse.accessToken());
    }

}
