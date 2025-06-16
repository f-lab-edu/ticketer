package com.ticketer.ticketing.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class VerificationCodeId implements Serializable {
    @Column(name="user_id")
    private Long id;
    @Column(name="verification_code")
    private String code;

}
