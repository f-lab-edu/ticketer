package com.ticketer.ticketing.service;

import com.ticketer.ticketing.util.RandomNumber;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class EmailVerificationService {

    private final EmailService emailService;

    /**
     * 신규회원 인증 이메일 전송
     * @param email 인증번호를 보낼 회원 이메일
     */
    @Transactional
    public void sendEmailVerification(Long userId, String email) {
        //1.인증번호 생성
        String code = RandomNumber.generateNumber(6);

        //2. 인증 코드 생성 및 이메일 전송
        emailService.sendEmailVerification(email, code);
    }

}
