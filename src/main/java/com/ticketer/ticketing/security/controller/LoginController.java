package com.ticketer.ticketing.security.controller;

import com.ticketer.ticketing.security.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    /**
     * 회원 로그인
     * @param loginDto
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginDto, HttpServletRequest request, HttpServletResponse response) {

        log.info("로그인 요청: {}",loginDto);

        //인증 객체 생성
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        //인증 처리
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //인증 정보 SecurityContext 저장
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);

        //인증 정보 세션 저장
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok().body("로그인 성공");

    }

    /**
     * 회원 로그아웃
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

        //인증 객체 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            //로그아웃 처리
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return ResponseEntity.ok().body("로그아웃 성공");
    }

}
