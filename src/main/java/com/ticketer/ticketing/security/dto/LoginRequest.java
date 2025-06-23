package com.ticketer.ticketing.security.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
public class LoginRequest {
    private String email;
    private String password;
}
