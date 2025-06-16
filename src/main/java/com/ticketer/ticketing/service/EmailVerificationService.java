package com.ticketer.ticketing.service;

import com.ticketer.ticketing.domain.entity.User;
import com.ticketer.ticketing.domain.entity.VerificationCode;
import com.ticketer.ticketing.domain.entity.VerificationCodeId;
import com.ticketer.ticketing.repository.UserRepository;
import com.ticketer.ticketing.repository.VerificationCodeRepository;
import com.ticketer.ticketing.util.RandomNumber;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Log4j2
public class EmailVerificationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    /**
     * 신규회원 인증 이메일 전송
     * @param email 인증번호를 보낼 회원 이메일
     */
    @Transactional
    public void sendEmailVerification(Long userId, String email) {
        //1.인증번호 생성
        String code = RandomNumber.generateNumber(6);

        //2. db저장
        saveVerificationCode(userId, code);

        //3. 인증 코드 생성 및 이메일 전송
        emailService.sendEmailVerification(email, code);
    }

    /**
     * 인증 코드 저장
     * @Param code
     */
    public void saveVerificationCode(Long userId, String code){
        //저장을 위해 VerificationCode 객체로 변환
        VerificationCode vc = VerificationCode.builder()
                .id(new VerificationCodeId(userId,code))
                .verifiedYn('N')
                .build();

        VerificationCode savedCodeInfo = verificationCodeRepository.save(vc);

        log.debug("인증 코드 저장:{}",savedCodeInfo);

    }

    /**
     * 인증 코드 유효성 검사
     * @param code
     */
    @Transactional
    public boolean verifyCode(Long userId, String code) {
        char verifiedYn = 'N';      //인증 완료 여부
        LocalDateTime now = LocalDateTime.now(); //만료시간과 비교할 현재 시각

        //일치하는 데이터 조회
        int cnt = verificationCodeRepository.countByIdIdAndIdCodeAndVerifiedYnAndExpiredDtAfter(
                userId, code, verifiedYn, now
        );

        log.debug("유효 인증 코드:{}개",cnt);

        if(cnt == 1){
            log.info("이메일 인증 성공");
            updateInfo(userId, code);
            return true;
        }else{
            log.info("이메일 인증 실패");
            return false;
        }
    }

    /**
     * 인증 완료 후 회원, 인증코드 상태 수정
     * @param userId 회원 아이디
     * @param code 이메일 인증 코드
     */
    @Transactional
    public void updateInfo(Long userId, String code){
        //1. 회원상태 Active로 수정
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 존재하지 않습니다."));
        user.updateUserStatusToActive();

        log.debug("user상태 변경->active:{}",user);

        //2. 인증완료여부 Y로 수정
        VerificationCode vc = verificationCodeRepository.findByIdIdAndIdCode(userId, code);
        vc.updateVerifiedYnToY();

        log.debug("코드 인증완료:{}",vc);

    }


}
