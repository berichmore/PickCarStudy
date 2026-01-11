package com.erp.global.auth;

import com.erp.domain.client.entity.Client;
import com.erp.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /*
        UserDetailsService

        Repository를 이용해 유저를 찾고,
                     유저를 만든다.
     */

//    private final ClientRepository clientRepository;

    private final ClientRepository clientRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. ClientRepository로 DB에서 유저 찾기
        return clientRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }



    // 2. Client Entity -> UserDetails 변환 메서드
    private UserDetails createUserDetails(Client client){
        return User.builder()
                .username(client.getEmail())
                .password(client.getPassword())  //db에 있는 암호화된 비밀번호여야 함
                .roles("employee")  // SecurityConfig와 맞춰야 함
                .build();
    }

}
