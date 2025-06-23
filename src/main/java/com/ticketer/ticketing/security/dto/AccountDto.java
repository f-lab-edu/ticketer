package com.ticketer.ticketing.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = "password")
public class AccountDto {

    private final Long id;
    private final String password;
    private final String name;
    private final String email;
    private final String phone;
    private final String address;
    private final String status;
    private final String role;

}
