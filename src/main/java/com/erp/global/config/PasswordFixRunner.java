package com.erp.global.config; // 패키지명은 상황에 맞게

import com.erp.domain.client.entity.Client;
import com.erp.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PasswordFixRunner implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("======================================");
        System.out.println("🔥 [비밀번호 긴급 점검 및 수정 시작] 🔥");

        String email = "test@email.com"; // 문제의 그 이메일
        String rawPassword = "1234";     // 내가 로그인할 진짜 비밀번호

        Client client = clientRepository.findByEmail(email).orElse(null);

        if (client == null) {
            System.out.println("❌ DB에서 유저(" + email + ")를 찾을 수 없습니다!");
        } else {
            // 1. 현재 DB에 저장된 값 확인
            System.out.println("🔍 현재 DB 암호값: " + client.getPassword());

            // 2. 매칭 테스트
            boolean matches = passwordEncoder.matches(rawPassword, client.getPassword());
            System.out.println("🔍 매칭 결과(현재): " + matches);

            if (!matches) {
                // 3. 안 맞으면 강제 업데이트
                String newHash = passwordEncoder.encode(rawPassword);
                client.setPassword(newHash);
                clientRepository.save(client); // 확실하게 저장
                System.out.println("✅ 비밀번호를 강제로 변경했습니다!");
                System.out.println("✅ 새로 만든 해시값: " + newHash);
                System.out.println("🚀 이제 '1234'로 로그인 다시 시도해보세요.");
            } else {
                System.out.println("❓ 매칭 된다는데? Postman에서 공백 들어갔는지 확인해보세요.");
            }
        }
        System.out.println("======================================");
    }
}