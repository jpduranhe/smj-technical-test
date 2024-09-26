package com.technical.test.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;

@Getter
@Setter
@ToString(exclude = "user")
@Entity
@Table(name = "phones")
public class PhoneEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @UuidGenerator
    private String id;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneEntity that)) return false;

        return Objects.equals(id, that.id) && Objects.equals(cityCode, that.cityCode) && Objects.equals(countryCode, that.countryCode) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(cityCode);
        result = 31 * result + Objects.hashCode(countryCode);
        result = 31 * result + Objects.hashCode(number);
        return result;
    }
}
