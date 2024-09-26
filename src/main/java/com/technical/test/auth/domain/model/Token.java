package com.technical.test.auth.domain.model;

import com.technical.test.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    private Integer id;

    private String token;


    private Boolean isRevoked;

    private Boolean isExpired;

    private User user;
}
