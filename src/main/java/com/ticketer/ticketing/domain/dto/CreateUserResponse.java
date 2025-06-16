package com.ticketer.ticketing.domain.dto;

import com.ticketer.ticketing.domain.enums.UserRole;
import com.ticketer.ticketing.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateUserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private UserStatus status;
    private UserRole role;

}
