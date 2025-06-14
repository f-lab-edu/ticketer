package com.ticketer.ticketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ticketer.ticketing.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}
