package com.ticketer.ticketing.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank
    @Size(min=1, max=20)
    private String name;

    @NotBlank
    @Email
    @Size(min=1, max=40)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$")
    private String phone;

    @Size(max=50)
    private String address;

}
