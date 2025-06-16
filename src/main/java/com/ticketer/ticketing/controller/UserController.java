package com.ticketer.ticketing.controller;

import com.ticketer.ticketing.domain.dto.CreateUserRequest;
import com.ticketer.ticketing.domain.dto.CreateUserResponse;
import com.ticketer.ticketing.domain.entity.User;
import com.ticketer.ticketing.service.EmailVerificationService;
import com.ticketer.ticketing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    /**
     * 회원가입
     * @param request 신규 회원 정보
     * @return 등록된 회원 정보
     */
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {

        log.debug("신규 사용자 생성 요청:{}",request);
        User user = userService.createUser(request);

        CreateUserResponse responseDto = CreateUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .status(user.getStatus())
                .role(user.getRole()).build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    /**
     * 이메일 중복체크
     * @param email 검사할 이메일 주소
     * @return boolean
     */
    @GetMapping("/email-check")
    public boolean checkEmail(@RequestParam String email){
        log.debug("이메일 중복체크 요청:{}",email);
        return userService.checkEmail(email);
    }

    /**
     * 전화번호 중복체크
     * @param phone 검사할 전화번호
     * @return boolean
     */
    @GetMapping("/phone-check")
    public boolean checkPhone(@RequestParam String phone){
        log.debug("전화번호 중복체크 요청:{}",phone);
        return userService.checkPhone(phone);
    }

    /**
     * 신규회원 이메일 인증번호 전송
     * [인증번호 재전송] 버튼 클릭 시 호출
     * @param
     * @return
     */
    @PostMapping("/email-verification")
    public ResponseEntity<?> sendEmailVerifications(@RequestParam String email){

        //로그인 기능 완료되면 사용자 정보에서 id, email을 가져오도록 수정 예정
        Long userId = 24L;
        log.info("이메일 인증 요청:{}",email);

        emailVerificationService.sendEmailVerification(userId, email);

        return ResponseEntity.ok("인증 메일 전송 완료");
    }

}



