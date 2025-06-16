package com.ticketer.ticketing.service;

import com.ticketer.ticketing.domain.entity.User;
import com.ticketer.ticketing.domain.dto.CreateUserRequest;
import com.ticketer.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    /**
     * 회원가입
     * @param request 신규 회원 정보
     * @return 생성된 회원 정보
     */
    public User createUser(CreateUserRequest request){
        //이메일 중복 검사
        if(checkEmail(request.getEmail())){
            log.warn("이메일 중복:{}",request.getEmail());
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        };
        //전화번호 중복 검사
        if(checkPhone(request.getPhone())){
            log.warn("전화번호 중복:{}",request.getPhone());
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        };

        //비밀번호 암호화
        String password = passwordEncoder.encode(request.getPassword());

        //User 엔티티 객체 생성
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(password)
                .phone(request.getPhone())
                .address(request.getAddress()).build();

        User savedUser = userRepository.save(user);

        //신규회원 이메일 인증 코드 발송
        emailVerificationService.sendEmailVerification(savedUser.getId(),user.getEmail());

        log.info("신규 사용자 생성 완료: userEmail={}",savedUser.getEmail());

        return savedUser;
    }

    /**
     * 이메일 중복체크
     * @param email 검사할 이메일 주소
     * @return 중복여부
     */
    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }

    /**
     * 전화번호 중복체크
     * @param phone 검사할 전화번호
     * @return 중복여부
     */
    public boolean checkPhone(String phone){
        return userRepository.existsByPhone(phone);
    }

}
