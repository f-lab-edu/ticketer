package com.ticketer.ticketing.domain.entity;

import com.ticketer.ticketing.domain.enums.UserRole;
import com.ticketer.ticketing.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",updatable=false)
    private Long id;

    @Column(name="user_name", nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String phone;

    @Column
    private String address;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;


    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime creatDt;

    @LastModifiedDate
    @Column
    private LocalDateTime updateDt;

    /**
     * 신규 회원 생성
     * @param name 이름
     * @param email 이메일
     * @param password 비밀번호
     * @param phone 전화번호
     * @param address 주소
     */
    @Builder
    public User(String name,String email,String password,String phone,String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.status = UserStatus.UNVERIFIED;
        this.role = UserRole.USER;
    }

}
