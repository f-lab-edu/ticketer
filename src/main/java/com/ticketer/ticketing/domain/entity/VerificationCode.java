package com.ticketer.ticketing.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Entity
@Table(name="verification_code")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Log4j2
public class VerificationCode {
    @EmbeddedId
    private VerificationCodeId id;
    @Column
    private LocalDateTime expiredDt; //만료기한
    private char verifiedYn;        //인증완료여부

    @PrePersist
    public void setExpiredDt() {
        //인증코드 -> 3분 후 만료
        expiredDt = LocalDateTime.now().plusMinutes(3);
    }

    /**
     * 코드상태 '인증완료'로 변경
     */
    public void updateVerifiedYnToY(){
        this.verifiedYn = 'Y';
    }

}
