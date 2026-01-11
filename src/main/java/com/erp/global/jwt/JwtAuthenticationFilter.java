package com.erp.global.jwt;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    /*
       SecurityFilterChain -> DelegatingFilterProxy의 인터셉트  -> Spring Security Chain
       -> Bean Filter(|| JwtAuthenticationFilter || - 바로 이 페이지 -) 실행
       -->
          1. header에서 Beaer<토큰> 추출
          2. JwtTokenProvider를 이용해 토큰 유효성 검사
          3. if (유효) SecurityContextHolder에 인증정보 저장
          4. 인증이 성공하면 Filter_2를 거쳐 Servlet으로 넘어가고, 실패하면 그 자리에서 401Error


        1. Header에서 토큰 꺼내기
        2. JwtTokenProvider로 검사하기( validateToken)
        3. 정품이면 '통과 도장'(Authentication)을 찍어서 SecurityContext라는 임시 보관함에 넣기
        4. chain.doFilter가 문열어줌


     */

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        // 1. Request Header에서 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateToken(token)){
            // 3. 토큰이 유효하면 유저 정보를 받아옴
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // 4. SecurityContext(임시 저장소)에 저장 -> 이제부터 스프링은 이 유저를 "로그인 된 사람"으로 취급
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 5. 다음 필터로 넘기기 ( 이게 없으면 요청이 여기서 멈춤)
        filterChain.doFilter(request, response);
    }




    // Header에서 "Bearer " 문자열 떼고 토큰만 발라내는 메서드
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }


}

































