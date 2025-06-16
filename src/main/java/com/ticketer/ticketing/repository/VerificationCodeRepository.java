package com.ticketer.ticketing.repository;

import com.ticketer.ticketing.domain.entity.VerificationCode;
import com.ticketer.ticketing.domain.entity.VerificationCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, VerificationCodeId> {

}
