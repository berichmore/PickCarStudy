package com.erp.domain.client.repository;

import com.erp.domain.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
//    UserDetails findByEmail(String email);
}
