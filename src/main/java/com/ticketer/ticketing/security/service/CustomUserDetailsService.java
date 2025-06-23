package com.ticketer.ticketing.security.service;

import com.ticketer.ticketing.security.dto.AccountContext;
import com.ticketer.ticketing.security.dto.AccountDto;
import com.ticketer.ticketing.domain.entity.User;
import com.ticketer.ticketing.domain.enums.UserStatus;
import com.ticketer.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //회원 조회
        User user = userRepository.findByEmailAndStatusNot(username, UserStatus.WITHDRAWN);
        if(user == null) {
            throw new UsernameNotFoundException("회원 미존재:" + username);
        }

        //회원 권한 목록
        List<GrantedAuthority> authorities
                = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        //User entity를 dto로 변환
        AccountDto accountDto = AccountDto.builder()
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .status(user.getStatus().name())
                .role(user.getRole().name())
                .build();

        log.debug("로그인 회원:{}",accountDto);

        return new AccountContext(accountDto,authorities);
    }
}
