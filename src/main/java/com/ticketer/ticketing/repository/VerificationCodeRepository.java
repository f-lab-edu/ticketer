package com.ticketer.ticketing.repository;

import com.ticketer.ticketing.domain.entity.VerificationCode;
import com.ticketer.ticketing.domain.entity.VerificationCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, VerificationCodeId> {
    int countByIdIdAndIdCodeAndVerifiedYnAndExpiredDtAfter(
            Long userId,
            String code,
            char verifiedYn,
            LocalDateTime now
    );

    VerificationCode findByIdIdAndIdCode(Long userId, String code);
}
