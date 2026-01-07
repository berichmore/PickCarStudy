package com.erp.domain.client.service;

import com.erp.domain.client.dto.request.LoginRequestDto;
import com.erp.global.jwt.JwtTokenProvider;
import com.erp.global.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(LoginRequestDto loginRequestDto){
        // 1. 인증되지 않은 ID/PW 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword());

        /*
            2. 실제 검증 단계 (가장 중요)
             여기서 SecurityConfig의 PasswordEncoder가 자동으로 작동합니다.

         */

        Authentication authentication = authenticationManager.authenticate(authenticationToken);



        // 3. 인증 정보를 가지고 JWT 토큰 생성 (엔진 가동)
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;

    }
}
