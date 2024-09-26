
package com.technical.test.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    private String cityCode;
    private String countryCode;
    private String number;
    private User user;

}
