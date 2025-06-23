package com.ticketer.ticketing.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.info(authException.getMessage());
        log.info("요청 URL: {}",request.getRequestURI());

        //응답 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //status code:401
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseEntity<String> body
                = new ResponseEntity<String>("인증이 필요합니다", HttpStatus.UNAUTHORIZED);

        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
