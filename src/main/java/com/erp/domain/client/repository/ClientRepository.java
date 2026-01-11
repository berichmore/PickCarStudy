package com.erp.domain.client.repository;

import com.erp.domain.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // 로그인
    Optional<Client> findByEmail(String email);

    // 이메일 중복 확인
    boolean existsByEmail(String email);

}
