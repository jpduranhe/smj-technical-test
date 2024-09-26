
package com.technical.test.user.domain.model;


import com.technical.test.user.domain.vo.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.technical.test.user.entity.UserEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;

    private Email email;

    private String name;

    private String password;

    private List<Phone> phones;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime lastLogin;

    private Boolean isActive;


}
