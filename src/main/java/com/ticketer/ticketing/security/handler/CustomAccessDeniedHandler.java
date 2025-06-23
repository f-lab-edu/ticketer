package com.ticketer.ticketing.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.info(accessDeniedException.getMessage());
        log.info("요청 URL: {}",request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); //status code:403
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseEntity<String> body
                = new ResponseEntity<>("접근 권한이 없습니다", HttpStatus.FORBIDDEN);

        response.getWriter().write(mapper.writeValueAsString(body));
    }

}
