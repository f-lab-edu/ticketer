package com.ticketer.ticketing.service;

import com.ticketer.ticketing.domain.dto.CreateUserRequest;
import com.ticketer.ticketing.domain.entity.User;
import com.ticketer.ticketing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    private CreateUserRequest user;

    @BeforeEach
    public void setUp() {
        user = CreateUserRequest.builder()
                .name("김하늘")
                .email("hn@email.com")
                .password("1234")
                .phone("010-0000-0000")
                .address("서울특별시").build();
    }
    @Test
    @DisplayName("신규회원 저장 성공")
    public void insertUser(){

        userService.createUser(user);

        verify(userRepository).save(any(User.class));

    }

    @Test
    @DisplayName("이메일 중복시 예외 발생")
    public void throwsExceptionWhenEmailIsDuplicated(){

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrowsExactly(IllegalArgumentException.class,()->{
                userService.createUser(user);
        });
    }

    @Test
    @DisplayName("전화번호 중복시 예외 발생")
    public void throwsExceptionWhenPhoneNumberIsDuplicated(){

        when(userRepository.existsByPhone(user.getPhone())).thenReturn(true);

        assertThrowsExactly(IllegalArgumentException.class,()->{
                userService.createUser(user);
        });
    }

    @Test
    @DisplayName("이메일이 중복되지 않을시 예외 미발생")
    public void doesNotThrowExceptionWhenEmailIsUnique(){

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertDoesNotThrow(()-> userService.createUser(user));
    }

    @Test
    @DisplayName("전화번호가 중복되지 않을시 예외 미발생")
    public void doesNotThrowExceptionWhenPhoneNumberIsUnique(){

        when(userRepository.existsByPhone(user.getPhone())).thenReturn(false);

        assertDoesNotThrow(()-> userService.createUser(user));
    }

}