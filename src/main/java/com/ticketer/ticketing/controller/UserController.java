package com.ticketer.ticketing.controller;

import com.ticketer.ticketing.domain.dto.CreateUserRequest;
import com.ticketer.ticketing.domain.dto.CreateUserResponse;
import com.ticketer.ticketing.domain.entity.User;
import com.ticketer.ticketing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param request 신규 회원 정보
     * @return 등록된 회원 정보
     */
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
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
        return userService.checkEmail(email);
    }

    /**
     * 전화번호 중복체크
     * @param phone 검사할 전화번호
     * @return boolean
     */
    @GetMapping("/phone-check")
    public boolean checkPhone(@RequestParam String phone){
        return userService.checkPhone(phone);
    }

}



