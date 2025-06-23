package com.ticketer.ticketing.config;

import com.ticketer.ticketing.domain.entity.User;
import com.ticketer.ticketing.domain.enums.UserStatus;
import com.ticketer.ticketing.repository.UserRepository;
import com.ticketer.ticketing.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService userDetailsService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("일치 회원 없으면 에러 반환")
    public void throwsExceptionWhenUserDoseNotExists(){
        String email = "abc@email.com";

        when(userRepository.findByEmailAndStatusNot(email, UserStatus.WITHDRAWN)).thenReturn(null);

        assertThrowsExactly(UsernameNotFoundException.class,()->{
            userDetailsService.loadUserByUsername(email);
        });

    }

    @Test
    @DisplayName("일치하는 회원 반환")
    public void ReturnUserDetailsWhenUserExists(){
        String email = "hn@email.com";
        User user = User.builder()
                .name("김하늘")
                .email(email)
                .password("1234")
                .phone("010-0000-0000")
                .address("서울특별시")
                .build();
        when(userRepository.findByEmailAndStatusNot(email, UserStatus.WITHDRAWN))
                .thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
        assertNotNull(userDetails);
        assertEquals(user.getEmail(),userDetails.getUsername());
        assertEquals(user.getPassword(),userDetails.getPassword());
        assertEquals(user.getRole().name(),authorities.get(0).getAuthority());

    }

}